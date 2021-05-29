package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.data.database.db.ReviewResponseDatabase
import com.hfad.capstone.data.database.db.TransactionDatabase
import com.hfad.capstone.data.database.networkBoundResource
import com.hfad.capstone.helper.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ReviewResponseRepository @Inject constructor(
    private val api: Api,
    private val db: ReviewResponseDatabase
) {
    private val reviewResponseDao = db.reviewResponseDao()

    fun getReview(productId:Int) = networkBoundResource(
        query = {
            reviewResponseDao.getReview(productId)
        },
        fetch = {
            api.readCrawlKomentar(productId)
        },
        saveFetchResult = { review ->
            db.withTransaction {
                reviewResponseDao.insertReview(DataMapper.mapReviewResponsesToEntities(review,productId))
            }
        }
    )


}