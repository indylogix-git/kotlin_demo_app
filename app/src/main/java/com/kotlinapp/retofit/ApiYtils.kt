package com.kotlinapp.retofit

object ApiUtils {

    const val BASE_URL = "http://192.168.20.110/demo/"
    val userService: UserService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(UserService::class.java)
}