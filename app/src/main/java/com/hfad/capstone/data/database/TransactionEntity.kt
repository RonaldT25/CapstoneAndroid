package com.hfad.capstone.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hfad.capstone.data.Product


@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey val Transactionid:Int,
    val TransactionstoreId: Int,
    val productId: Int,
    val time: String,
    val amount: Int,
    @Embedded
    val product: Product
)