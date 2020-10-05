package com.joshua.mvvmwithflow.data.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joshua.mvvmwithflow.data.model.UserObject

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserList(userObject: UserObject)

    @Query("DELETE FROM UserObject where id = :id")
    fun removeUserList(id: String)

    @Query("SELECT * FROM UserObject")
    fun getUserList(): List<UserObject>

}