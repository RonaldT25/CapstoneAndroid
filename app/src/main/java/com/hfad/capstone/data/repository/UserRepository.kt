package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.ProductDatabase
import com.hfad.capstone.data.database.db.UserDatabase
import com.hfad.capstone.data.database.networkBoundResource
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val api: Api,
        private val db: UserDatabase
) {
    private val userDao = db.userDao()

    fun getProfile() = networkBoundResource(
            query = {
                userDao.getAllUsers()
            },
            fetch = {
                api.getProfile()
            },
            saveFetchResult = { result ->
                db.withTransaction {
                    userDao.insertUser(result)
                }
            }
    )
}