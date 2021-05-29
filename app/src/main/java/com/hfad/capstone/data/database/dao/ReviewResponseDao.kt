package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.database.ReviewResponseEntity
import com.hfad.capstone.data.database.TransactionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ReviewResponseDao {
    @Query("SELECT * FROM review WHERE productId LIKE :productId")
    fun getReview(productId : Int): Flow<ReviewResponseEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertReview(review : ReviewResponseEntity)
    @Query("DELETE FROM review")
    suspend fun deleteAllReview()
}