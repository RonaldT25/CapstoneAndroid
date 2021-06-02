package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.ResponseSalesDatabase
import com.hfad.capstone.data.database.db.ReviewResponseDatabase
import com.hfad.capstone.data.database.networkBoundResource
import com.hfad.capstone.helper.DataMapper
import javax.inject.Inject

class ResponseSalesRepository @Inject constructor(
    private val api: Api,
    private val db: ResponseSalesDatabase
) {
    private val reviewResponseDao = db.responseSalesDao()

    fun getReview(productId:Int) = networkBoundResource(
        query = {
            reviewResponseDao.getAnalyzeSales(productId)
        },
        fetch = {
            api.readAnalyzeSales(productId)
        },
        saveFetchResult = { review ->
            db.withTransaction {
                reviewResponseDao.insertAnalyzeSales(DataMapper.mapAnalyzeSalesToEntities(review,productId))
            }
        }
    )

    fun getReviewFromDb(productId:Int) = networkBoundResource(
        query = {
            reviewResponseDao.getAnalyzeSales(productId)
        },
        fetch = {
            api.readAnalyzeSales(productId)
        },
        saveFetchResult = { review ->
            db.withTransaction {
                reviewResponseDao.insertAnalyzeSales(DataMapper.mapAnalyzeSalesToEntities(review,productId))
            }
        },
        shouldFetch = { false }
    )


}