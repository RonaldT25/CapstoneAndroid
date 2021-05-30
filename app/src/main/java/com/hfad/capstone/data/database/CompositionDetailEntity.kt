package com.hfad.capstone.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.hfad.capstone.data.model.Composition
import com.hfad.capstone.data.model.Product

@Entity(tableName = "compositionDetail")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class CompositionDetailEntity(
        @PrimaryKey val CompositionDetailId:Int,
        val productId: Int,
        val amount: Float,
        @Embedded
        val composition: Composition,
        @Embedded
        val product: Product
)