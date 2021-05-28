package com.hfad.capstone.helper

import com.hfad.capstone.data.Product
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.data.database.TransactionEntity

object DataMapper {
    fun mapResponsesToEntities(input: List<Transaction>): List<TransactionEntity> {
        val transactionList = ArrayList<TransactionEntity>()
        input.map {
            val transaction = TransactionEntity(
                Transactionid = it.id,
                TransactionstoreId = it.storeId,
                productId = it.productId,
                time = it.time,
                amount = it.amount,
                product = it.product
            )
            transactionList.add(transaction)
        }
        return transactionList
    }
    fun mapEntitiesToDomain(input: List<TransactionEntity>): List<Transaction> =
        input.map {
            Transaction(
                id = it.Transactionid,
                storeId = it.TransactionstoreId,
                productId = it.productId,
                time = it.time,
                amount = it.amount,
                product = it.product
            )
        }


}