package com.joshua.mvvmwithflow.data.repo.user

import com.joshua.mvvmwithflow.data.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    fun getUsers(): Flow<ArrayList<User>?>

    fun getUsersAllowCache(): Flow<ArrayList<User>?>

    fun getNumberOfLikes(id: String): Flow<Int?>
}