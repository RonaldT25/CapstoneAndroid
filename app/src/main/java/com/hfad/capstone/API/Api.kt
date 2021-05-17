package com.hfad.capstone.API

import com.hfad.capstone.data.ResponseAuth
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {


    @POST("api/users/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("role") role:String
    ): Call<ResponseAuth>


    @POST("api/users/login")
    @FormUrlEncoded
    fun login(
            @Field("username") username:String,
            @Field("password") password:String
    ): Call<ResponseAuth>



}