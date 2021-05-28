package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.CompositionDatabase
import com.hfad.capstone.data.database.db.ProductDatabase
import com.hfad.capstone.data.database.networkBoundResource
import javax.inject.Inject

class CompositionRepository @Inject constructor(
    private val api: Api,
    private val db: CompositionDatabase
) {
    private val transactionDao = db.compositionDao()

    fun getCompositions() = networkBoundResource(
        query = {
            transactionDao.getAllCompositions()
        },
        fetch = {
            api.readComposition()
        },
        saveFetchResult = { compositions ->
            db.withTransaction {
                transactionDao.deleteAllCompositions()
                transactionDao.insertCompositions(compositions)
            }
        }
    )
}