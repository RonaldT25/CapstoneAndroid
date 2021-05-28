package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.data.database.db.TransactionDatabase
import com.hfad.capstone.data.database.networkBoundResource
import com.hfad.capstone.helper.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val api: Api,
    private val db: TransactionDatabase
) {
    private val transactionDao = db.transactionDao()

    fun getTransactions() = networkBoundResource(
        query = {
            transactionDao.getAllTransactions()
        },
        fetch = {
            api.readTransaction()
        },
        saveFetchResult = { transactions ->
            db.withTransaction {
                transactionDao.deleteAllTransactions()
                transactionDao.insertTransactions(DataMapper.mapResponsesToEntities(transactions))
            }
        }
    )

    fun search(searchQuery:String) : Flow<List<TransactionEntity>> {
        return transactionDao.search(searchQuery)
    }
}