package com.joshua.mvvmwithflow.network

import com.joshua.mvvmwithflow.data.model.User
import retrofit2.http.GET


interface jsonplaceholderService {

    // suspend + no Call<> return makes Coroutine return (Method 2)

    @GET("users")
    suspend fun getUsers(): ArrayList<User>?

}