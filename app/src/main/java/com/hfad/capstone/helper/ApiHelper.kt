package com.hfad.capstone.helper

import com.hfad.capstone.api.Api

class ApiHelper(private val api:Api) {
    suspend fun getProfile() = api.getProfile()
    suspend fun readTransaction() = api.readTransaction()
    suspend fun readProduct() = api.readProduct()
    suspend fun readComposition() = api.readComposition()
    suspend fun readStore() = api.readStore()
}