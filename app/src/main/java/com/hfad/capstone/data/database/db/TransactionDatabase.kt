package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.data.database.dao.TransactionDao

@Database(entities = [TransactionEntity::class],version = 1)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao() : TransactionDao
}