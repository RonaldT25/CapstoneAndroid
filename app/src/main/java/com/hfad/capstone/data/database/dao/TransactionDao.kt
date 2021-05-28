package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.data.database.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
     fun getAllTransactions(): Flow<List<TransactionEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertTransactions(transactions : List<TransactionEntity>)
    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransactions()
}