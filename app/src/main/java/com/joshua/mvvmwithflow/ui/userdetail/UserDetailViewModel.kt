package com.joshua.mvvmwithflow.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.joshua.mvvmwithflow.BaseApplication
import com.joshua.mvvmwithflow.data.model.User
import com.joshua.mvvmwithflow.di.ServiceLocator
import kotlinx.coroutines.flow.map

class UserDetailViewModel : ViewModel() {

    private val userRepo = ServiceLocator.provideUserRepository(BaseApplication.appContext)

    fun getUserDetail(id: String): LiveData<User?> =
        userRepo.getUsersAllowCache()
            .map {
                if (it != null) {
                    for (user in it) {
                        if (user.id == id) {
                            return@map user
                        }
                    }
                }
                return@map null
            }.asLiveData()


}