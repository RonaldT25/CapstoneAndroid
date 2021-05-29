package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hfad.capstone.data.database.ReviewResponseEntity
import com.hfad.capstone.data.database.ReviewTypeConverter
import com.hfad.capstone.data.database.dao.ReviewResponseDao

@Database(entities = [ReviewResponseEntity::class],version = 1)
@TypeConverters(ReviewTypeConverter::class)
abstract class ReviewResponseDatabase : RoomDatabase() {
    abstract fun reviewResponseDao() : ReviewResponseDao
}