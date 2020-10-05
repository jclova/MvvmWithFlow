package com.joshua.mvvmwithflow.di

import android.content.Context
import com.google.gson.Gson
import com.joshua.mvvmwithflow.data.repo.MyDatabase
import com.joshua.mvvmwithflow.data.repo.user.UserRepository
import com.joshua.mvvmwithflow.data.repo.user.UserRepositoryImpl
import com.joshua.mvvmwithflow.network.NetworkServiceFactory
import com.joshua.mvvmwithflow.network.jsonplaceholderService
import com.joshua.mvvmwithflow.util.SharedPrefUtil


object ServiceLocator {


    @Volatile
    private var userRepository: UserRepository? = null

    fun provideUserRepository(context: Context): UserRepository {
        return userRepository ?: synchronized(this) {
            userRepository ?: createUserRepository(context)
        }
    }

    private fun createUserRepository(context: Context): UserRepository {
        val database = MyDatabase.getInstance(context)
        val sharedPrefUtil = getSharedPreferences(context)
        val repo = UserRepositoryImpl(database.getDao(), sharedPrefUtil)
        userRepository = repo
        return repo
    }

    @Volatile
    private var jsonplaceholderService: jsonplaceholderService? = null

    fun provideJsonHolderService(): jsonplaceholderService {
        return jsonplaceholderService ?: synchronized(this) {
            jsonplaceholderService ?: NetworkServiceFactory.createJsonHolderService()
        }
    }

    @Volatile
    private var gson: Gson? = null

    fun getGson(): Gson {
        return gson ?: synchronized(this) { gson ?: Gson() }
    }


    @Volatile
    private var sharedPreferences: SharedPrefUtil? = null

    fun getSharedPreferences(context: Context): SharedPrefUtil {
        return sharedPreferences ?: synchronized(this) {
            sharedPreferences ?: SharedPrefUtil(context)
        }
    }

}
