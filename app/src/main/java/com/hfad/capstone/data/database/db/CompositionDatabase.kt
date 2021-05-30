package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.model.Composition
import com.hfad.capstone.data.database.dao.CompositionDao

@Database(entities = [Composition::class],version = 2,exportSchema = false)
abstract class CompositionDatabase : RoomDatabase() {
    abstract fun compositionDao() : CompositionDao
}