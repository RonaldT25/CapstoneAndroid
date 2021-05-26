package com.hfad.capstone.api


import com.hfad.capstone.data.*
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

     @PUT(Constants.GETPROFILE_URL)
     @FormUrlEncoded
     fun updateProfile(@Header("auth-token") token:String,
                       @Field("username") username:String,
                       @Field("email") email:String) : Call<updateResponse>

    @PUT(Constants.GETPROFILE_URL)
    @FormUrlEncoded
    fun updateProfilePassword(@Header("auth-token") token:String,
                      @Field("password") password:String) : Call<updateResponse>



    @POST(Constants.READPRODUCT_URL)
    @FormUrlEncoded
    fun createProduct(@Header("auth-token") token:String,
                      @Field("productName") productName:String,
                      @Field("price") price:Int,
                      @Field("image") image:String) : Call<Product>

    @GET(Constants.READPRODUCT_URL)
    fun readProduct(@Header("auth-token") token:String ) : Call<List<Product>>

    @PUT("api/users/stores/products/{productId}")
    @FormUrlEncoded
    fun updateProduct(@Path("productId") productId:Int,
                      @Field("productName") productName:String,
                      @Field("price") price:Int,
                      @Header("auth-token") token:String ) : Call<updateResponse>


    @DELETE("api/users/stores/products/{productId}")
    fun deleteProduct(@Path("productId") productId:Int,
                      @Header("auth-token") token:String ) : Call<updateResponse>



    @POST(Constants.READCOMPOSITION_URL)
    @FormUrlEncoded
    fun createComposition(
        @Header("auth-token") token:String,
        @Field("compositionName") compositionName:String,
        @Field("unit") unit:String,
    ): Call<Composition>

    @GET(Constants.READCOMPOSITION_URL)
    fun readComposition(
        @Header("auth-token") token:String
    ): Call<List<Composition>>

    @PUT("api/users/stores/compositions/{compositionId}")
    @FormUrlEncoded
    fun updateComposition(@Path("compositionId") compositionId:Int,
                      @Field("compositionName") compositionName:String,
                      @Field("unit") unit:String,
                      @Header("auth-token") token:String ) : Call<updateResponse>

    @DELETE("api/users/stores/compositions/{compositionId}")
    fun deleteComposition(@Path("compositionId") compositionId:Int,
                          @Header("auth-token") token:String ) : Call<updateResponse>


    @GET(Constants.READSTORE_URL)
    fun readStore(@Header("auth-token") token:String ) : Call<StoreResponse>

    @PUT(Constants.READSTORE_URL)
    @FormUrlEncoded
    fun updateStore(@Header("auth-token") token:String,
                    @Field("storeName") storeName:String,
                    @Field("description") description:String,) : Call<updateResponse>

}