package com.hfad.capstone.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
        val id:Int,
        val productName: String,
        val storeId: Int,
        val price: Int,
        val image: String?,
        val tokopediaProductId: Int?,
        val tokopediaProductUrl: String?
) : Parcelable
