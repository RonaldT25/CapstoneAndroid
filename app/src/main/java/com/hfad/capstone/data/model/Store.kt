package com.hfad.capstone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class Store(
        @PrimaryKey val id:Int,
        val storeName:String,
        val userId:Int,
        val description:String
)
