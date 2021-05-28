package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.Store
import com.hfad.capstone.data.User
import com.hfad.capstone.data.database.dao.StoreDao
import com.hfad.capstone.data.database.dao.UserDao

@Database(entities = [Store::class],version = 1)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun storeDao() : StoreDao
}