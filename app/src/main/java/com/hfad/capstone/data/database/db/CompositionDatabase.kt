package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.Composition
import com.hfad.capstone.data.database.dao.CompositionDao

@Database(entities = [Composition::class],version = 1)
abstract class CompositionDatabase : RoomDatabase() {

    abstract fun compositionDao() : CompositionDao
}