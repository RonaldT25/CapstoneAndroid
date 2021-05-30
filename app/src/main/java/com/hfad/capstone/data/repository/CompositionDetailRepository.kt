package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.CompositionDetailDatabase
import com.hfad.capstone.data.database.networkBoundResource
import com.hfad.capstone.helper.DataMapper
import javax.inject.Inject

class CompositionDetailRepository @Inject constructor(
        private val api: Api,
        private val db: CompositionDetailDatabase
) {
    private val compositionDetailDao = db.compositionDetailDao()

    fun getCompositionsDetail(productId:Int) = networkBoundResource(
            query = {
                compositionDetailDao.getAllCompositionsDetail()
            },
            fetch = {
                api.readCompositionDetail(productId)
            },
            saveFetchResult = { compositions ->
                db.withTransaction {
                    compositionDetailDao.deleteAllCompositionsDetail()
                    compositionDetailDao.insertCompositionsDetail(DataMapper.mapCompositionDetailToEntities(compositions))
                }
            }
    )
}