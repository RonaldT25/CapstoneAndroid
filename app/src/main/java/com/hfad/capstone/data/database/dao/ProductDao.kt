package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertProducts(Products : List<Product>)
    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()
    @Query("SELECT id FROM product WHERE productName LIKE :searchQuery")
    fun search(searchQuery:String) : Flow<Int>
}