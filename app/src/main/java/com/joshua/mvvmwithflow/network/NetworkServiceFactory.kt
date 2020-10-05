package com.joshua.mvvmwithflow.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceFactory {


    fun createJsonHolderService(): jsonplaceholderService {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

        return retrofit.create(jsonplaceholderService::class.java)
    }

}