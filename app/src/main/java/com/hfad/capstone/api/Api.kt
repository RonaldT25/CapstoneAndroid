package com.hfad.capstone.api


import com.hfad.capstone.data.model.*
import com.hfad.capstone.helper.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST(Constants.SESSION_URL)
    @FormUrlEncoded
     fun checkToken(
        @Field("token") token:String,
    ): Call<ResponseAuth>

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
    suspend fun getProfile() : User

    @PUT(Constants.GETPROFILE_URL)
    @FormUrlEncoded
    suspend fun updateProfile(
                      @Field("username") username:String,
                      @Field("email") email:String) : Response<updateResponse>

    @PUT(Constants.GETPROFILE_URL)
    @FormUrlEncoded
    suspend fun updateProfilePassword(
                              @Field("password") password:String) : Response<updateResponse>



    @POST(Constants.READPRODUCT_URL)
    @FormUrlEncoded
    suspend fun createProduct(
                      @Field("productName") productName:String,
                      @Field("price") price:Int,
                      @Field("image") image:String) : Response<Product>

    @GET(Constants.READPRODUCT_URL)
    suspend fun readProduct() : List<Product>

    @PUT("api/users/stores/products/{productId}")
    @FormUrlEncoded
    suspend fun updateProduct(@Path("productId") productId:Int,
                      @Field("productName") productName:String,
                      @Field("price") price:Int,
                       ) : Response<updateResponse>


    @DELETE("api/users/stores/products/{productId}")
    suspend fun deleteProduct(@Path("productId") productId:Int) : Response<updateResponse>



    @POST(Constants.READCOMPOSITION_URL)
    @FormUrlEncoded
    suspend fun createComposition(
        @Field("compositionName") compositionName:String,
        @Field("unit") unit:String,
    ): Response<Composition>

    @GET(Constants.READCOMPOSITION_URL)
    suspend fun readComposition(): List<Composition>

    @PUT("api/users/stores/compositions/{compositionId}")
    @FormUrlEncoded
    suspend fun updateComposition(@Path("compositionId") compositionId:Int,
                          @Field("compositionName") compositionName:String,
                          @Field("unit") unit:String) : Response<updateResponse>

    @DELETE("api/users/stores/compositions/{compositionId}")
    suspend fun deleteComposition(@Path("compositionId") compositionId:Int) : Response<updateResponse>


    @GET("api/users/stores/products/{productId}/compositions/")
    suspend fun readCompositionDetail(@Path("productId") productId:Int) : List<CompositionDetail>

    @POST("api/users/stores/products/{productId}/compositions/")
    @FormUrlEncoded
    suspend fun insertCompositionDetail(@Path("productId") productId:Int,
                                        @Field("compositionId") compositionId:Int,
                                        @Field("productId") productId2:Int,
                                        @Field("amount") amount:Float,) : Response<updateResponse>

    @DELETE("api/users/stores/products/{productId}/compositions/{compositionId}")
    suspend fun deleteCompositionDetail(@Path("productId") productId:Int,
                                        @Path("compositionId")compositionId:Int) : Response<updateResponse>


    @GET(Constants.READSTORE_URL)
    suspend fun readStore() : StoreResponse

    @PUT(Constants.READSTORE_URL)
    @FormUrlEncoded
    suspend fun updateStore( @Field("storeName") storeName:String,
                    @Field("description") description:String,) : Response<updateResponse>

    @GET("api/users/stores/products/{productId}/reviews")
    suspend fun readCrawlKomentar(@Path("productId") productId:Int ) : ReviewResponse

    @GET(Constants.TRANSACTION_URL)
    suspend fun readTransaction() : List<Transaction>

    @POST("api/users/stores/products/{productId}/transactions")
    @FormUrlEncoded
    suspend fun insertTransaction(@Path("productId") productId:Int,
                                        @Field("productId") productId2:Int,
                                        @Field("time") time:String,
                                        @Field("amount") amount:Int,) : Response<updateResponse>


}