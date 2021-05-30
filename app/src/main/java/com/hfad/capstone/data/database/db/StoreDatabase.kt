package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.model.Store
import com.hfad.capstone.data.database.dao.StoreDao

@Database(entities = [Store::class],version = 1,exportSchema = false)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun storeDao() : StoreDao
}