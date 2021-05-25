package com.hfad.capstone.api


import com.hfad.capstone.data.Product
import com.hfad.capstone.data.ResponseAuth
import com.hfad.capstone.data.User
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.helper.Constants
import retrofit2.Call
import retrofit2.http.*

interface Api {


    @POST(Constants.REGISTER_URL)
    @FormUrlEncoded
    fun register(
        @Field("username") username:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("role") role:String
    ): Call<ResponseAuth>


    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
            @Field("username") username:String,
            @Field("password") password:String
    ): Call<ResponseAuth>

    @GET(Constants.GETPROFILE_URL)
     fun getProfile(@Header("auth-token") token:String ) : Call<User>

    @GET(Constants.READPRODUCT_URL)
    fun readProduct(@Header("auth-token") token:String ) : Call<List<Product>>

    @PUT("api/users/stores/products/{productId}")
    @FormUrlEncoded
    fun updateProduct(@Path("productId") productId:Int,
                      @Field("productName") productName:String,
                      @Field("price") price:Int,
                      @Header("auth-token") token:String ) : Call<updateResponse>



}