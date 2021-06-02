package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.data.model.ResponseSales
import kotlinx.coroutines.flow.Flow

@Dao
interface ResponseSalesDao {
    @Query("SELECT * FROM analyzeSales WHERE productId LIKE :productId")
    fun getAnalyzeSales(productId : Int): Flow<ResponseSalesEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertAnalyzeSales(analyzeSales : ResponseSalesEntity)
    @Query("DELETE FROM analyzeSales")
    suspend fun deleteAllAnalyzeSales()
}