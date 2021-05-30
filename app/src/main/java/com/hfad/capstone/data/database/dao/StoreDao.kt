package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.model.Store
import kotlinx.coroutines.flow.Flow


@Dao
interface StoreDao {
    @Query("SELECT * FROM store")
    fun getAllStores(): Flow<Store>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertStore(store : Store)
    @Query("DELETE FROM store")
    suspend fun deleteAllStores()
}