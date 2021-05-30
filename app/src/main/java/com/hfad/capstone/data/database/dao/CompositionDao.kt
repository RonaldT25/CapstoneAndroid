package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.model.Composition
import kotlinx.coroutines.flow.Flow

@Dao
interface CompositionDao {
    @Query("SELECT * FROM composition")
    fun getAllCompositions(): Flow<List<Composition>>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertCompositions(Compositions : List<Composition>)
    @Query("DELETE FROM composition")
    suspend fun deleteAllCompositions()
    @Query("SELECT compositionId FROM composition WHERE compositionName LIKE :searchQuery")
    fun search(searchQuery:String) : Flow<Int>
}