package com.joshua.mvvmwithflow.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class UserObject(
    @PrimaryKey
    var id: String,
    var payload: String
)


