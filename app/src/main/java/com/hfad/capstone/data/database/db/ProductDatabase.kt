package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.database.dao.ProductDao

@Database(entities = [Product::class],version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao() : ProductDao
}