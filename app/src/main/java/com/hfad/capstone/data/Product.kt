package com.hfad.capstone.data

data class Product(
        val productName: String,
        val storeId: Int,
        val price: Int,
        val image: String?,
        val tokopediaProductId: Int?,
        val tokopediaProductUrl: String?
)
