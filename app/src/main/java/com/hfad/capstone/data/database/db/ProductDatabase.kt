package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.data.database.dao.ProductDao

@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao() : ProductDao
}