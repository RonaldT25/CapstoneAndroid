package com.hfad.capstone.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "composition")
data class Composition(
        @SerializedName("id")
    @PrimaryKey val compositionId:Int,
    val compositionName: String,
        @SerializedName("storeId")
    val compositionStoreId: Int,
    val unit: String
) : Parcelable
