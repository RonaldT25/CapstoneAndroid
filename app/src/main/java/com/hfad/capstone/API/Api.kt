package com.hfad.capstone.API

import com.hfad.capstone.data.RegisterUser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("api/users/register")
    fun register(
        @Field("username") username:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("role") role:String
    ): Call<RegisterUser>
}