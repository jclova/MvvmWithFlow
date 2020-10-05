package com.joshua.mvvmwithflow.data.model

import androidx.annotation.Keep

@Keep
data class User(
    val id: String? = null,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val address: Address? = null,
    val phone: String? = null,
    val website: String? = null,
    val company: Company? = null
)

@Keep
data class Address(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
    val geo: Geo? = null
)

@Keep
data class Company(
    val name: String? = null,
    val catchPhrase: String? = null,
    val bs: String? = null
)

@Keep
data class Geo(
    val lat: Double? = null,
    val lng: Double? = null
)

@Keep
data class UserList(
    var value: ArrayList<User>? = null
)
