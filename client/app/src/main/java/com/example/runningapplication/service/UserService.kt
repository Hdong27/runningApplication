package com.example.runningapplication.service

import com.example.runningapplication.data.model.User
import retrofit2.http.*
import retrofit2.Call
import java.util.*
import kotlin.collections.HashMap

interface UserService {

//    @FormUrlEncoded
    @POST("/signup.run")
    fun signUp(
        @Body parameters: HashMap<String,Any>
    ):Call<Boolean>

    @POST("/login.run")
    fun login(
        @Body parameters: HashMap<String,Any>
    ):Call<User>

    @POST("/overlap.run")
    fun emailCheck(
        @Body parameters: HashMap<String,Any>
    ):Call<Boolean>

}

