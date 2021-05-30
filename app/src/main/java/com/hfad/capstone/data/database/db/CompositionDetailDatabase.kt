package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.database.CompositionDetailEntity
import com.hfad.capstone.data.database.dao.CompositionDetailDao

@Database(entities = [CompositionDetailEntity::class],version = 1,exportSchema = false)
abstract class CompositionDetailDatabase : RoomDatabase() {
    abstract fun compositionDetailDao() : CompositionDetailDao
}