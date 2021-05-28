package com.hfad.capstone.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "composition")
data class Composition(
    @PrimaryKey val id:Int,
    val compositionName: String,
    val storeId: Int,
    val unit: String
) : Parcelable
