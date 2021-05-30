package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.database.CompositionDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompositionDetailDao {
    @Query("SELECT * FROM compositionDetail")
    fun getAllCompositionsDetail(): Flow<List<CompositionDetailEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertCompositionsDetail(Compositions : List<CompositionDetailEntity>)
    @Query("DELETE FROM compositionDetail")
    suspend fun deleteAllCompositionsDetail()
}