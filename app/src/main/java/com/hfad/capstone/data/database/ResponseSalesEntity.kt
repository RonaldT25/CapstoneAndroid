package com.hfad.capstone.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analyzeSales")
data class ResponseSalesEntity(
    @PrimaryKey val productId:Int,
    val compositions:String,
    val next_6_months:String,
    val start_next_6_months:String,
)