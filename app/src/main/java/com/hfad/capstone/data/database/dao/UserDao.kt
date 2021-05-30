package com.hfad.capstone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfad.capstone.data.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<User>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertUser(user : User)
    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}