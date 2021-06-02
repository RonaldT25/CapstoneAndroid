package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.data.database.dao.ResponseSalesDao

@Database(entities = [ResponseSalesEntity::class],version = 1,exportSchema = false)
abstract class ResponseSalesDatabase : RoomDatabase() {
    abstract fun responseSalesDao() : ResponseSalesDao
}