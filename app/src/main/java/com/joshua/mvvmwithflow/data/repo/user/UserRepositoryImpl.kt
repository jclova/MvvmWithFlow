package com.joshua.mvvmwithflow.data.repo.user

import com.joshua.mvvmwithflow.data.model.User
import com.joshua.mvvmwithflow.data.model.UserList
import com.joshua.mvvmwithflow.data.model.UserObject
import com.joshua.mvvmwithflow.data.repo.MyDao
import com.joshua.mvvmwithflow.di.ServiceLocator
import com.joshua.mvvmwithflow.util.SharedPrefUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class UserRepositoryImpl(
    private val myDao: MyDao,
    private val sharedPrefUtil: SharedPrefUtil
) : UserRepository {

    private var currentUserList: ArrayList<User>? = null


    override fun getUsers(): Flow<ArrayList<User>?> {
        return flow {

            // To show the data quickly as possible, first show data from cache, then from server

            var hasCacheData = false
            // 1. Emit first from either from memory or local
            // Memory
            if (currentUserList != null) {
                hasCacheData = true
                emit(currentUserList!!)
            } else {
                // Local
                val localCache = myDao.getUserList()
                if (localCache.isNotEmpty()) {
                    val users =
                        ServiceLocator.getGson()
                            .fromJson(localCache[0].payload, UserList::class.java)
                    if (users?.value != null) {
                        // Save to memory
                        currentUserList = users.value!!

                        hasCacheData = true
                        emit(users.value!!)
                    }
                }
            }

            // 2. Emit again from the network (for the latest update)
            // If API call is like a current stock price, we should auto refresh every 1 seconds. Then we use "while" loop with "delay(1000)"
//            while (true) {

            // Network
            val apiResult = ServiceLocator.provideJsonHolderService().getUsers()

            // Saving to DB takes time. Create another thread to save to DB.
            GlobalScope.launch(Dispatchers.IO) {
                saveToLocal(apiResult)
            }

            // Do not overwrite cache value with null network value (offline case)
            // If cache has no data, then it's ok to return null.
            if (apiResult != null || !hasCacheData)
                emit(apiResult)

//                delay(1000)
//            }


        }.flowOn(Dispatchers.IO)
    }

    override fun getUsersAllowCache(): Flow<ArrayList<User>?> {
        return flow {

            // If cache is found, don't try to download from server

            // 1. Memory
            if (currentUserList != null) {
                emit(currentUserList!!)
            } else {
                // 2. Local
                var hasLocalData = false
                val localCache = myDao.getUserList()
                if (localCache.isNotEmpty()) {
                    val users =
                        ServiceLocator.getGson()
                            .fromJson(localCache[0].payload, UserList::class.java)
                    if (users?.value != null) {
                        // Save to memory
                        currentUserList = users.value!!

                        hasLocalData = true
                        emit(users.value!!)
                    }
                }

                if (!hasLocalData) {
                    // 3. Network
                    val apiResult = ServiceLocator.provideJsonHolderService().getUsers()

                    // Saving to DB takes time. Create another thread to save to DB.
                    GlobalScope.launch(Dispatchers.IO) {
                        saveToLocal(apiResult)
                    }
                    emit(apiResult)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun saveToLocal(userList: ArrayList<User>?) {
        if (userList == null || userList.isEmpty())
            return

        // Memory
        currentUserList = userList

        // Local
        for (user in userList) {
            val payload = ServiceLocator.getGson().toJson(user)
            val userObject = UserObject(user.id ?: "-1", payload)
            myDao.addUserList(userObject)
        }
    }

    override fun getNumberOfLikes(id: String): Flow<Int?> {
        return flow {
            var counter = 0
            while (true) {
                // Mock number of likes.
                // Normally, it could have been retrieved from the server
                emit(counter)
                delay(1000) // 1 second
                counter++
            }
        }.map {
            // For example, "new value = value x 2"
            return@map it * 2
        }.flowOn(Dispatchers.IO) // if it was a real network call (not a mock), this is needed.
    }


}