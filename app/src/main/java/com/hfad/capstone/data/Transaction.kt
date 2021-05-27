package com.hfad.capstone.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id:Int,
    val storeId: Int,
    val productId: Int,
    val time: String,
    val amount: Int,
    val product: Product
) : Parcelable
