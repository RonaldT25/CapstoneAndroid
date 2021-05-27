package com.hfad.capstone.helper

class Repository(private val apiHelper: ApiHelper) {
    suspend fun getProfile() = apiHelper.getProfile()
    suspend fun readTransaction() = apiHelper.readTransaction()
    suspend fun readProduct() = apiHelper.readProduct()
    suspend fun readComposition() = apiHelper.readComposition()
    suspend fun readStore() = apiHelper.readStore()
}