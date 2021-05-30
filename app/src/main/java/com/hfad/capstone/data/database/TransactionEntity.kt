package com.hfad.capstone.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.hfad.capstone.data.model.Product


@Entity(tableName = "transaction")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class TransactionEntity(
    @PrimaryKey val Transactionid:Int,
    val TransactionstoreId: Int,
    val productId: Int,
    val time: String,
    val amount: Int,
    @Embedded
    val product: Product
)