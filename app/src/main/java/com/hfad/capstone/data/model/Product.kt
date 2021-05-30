package com.hfad.capstone.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product(
        @PrimaryKey val id:Int,
        val productName: String,
        val storeId: Int,
        val price: Int,
        val image: String?,
        val tokopediaProductId: Int?,
        val tokopediaProductUrl: String?
) : Parcelable
