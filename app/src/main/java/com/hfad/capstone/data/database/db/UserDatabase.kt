package com.hfad.capstone.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.capstone.data.User
import com.hfad.capstone.data.database.dao.UserDao

@Database(entities = [User::class],version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
}