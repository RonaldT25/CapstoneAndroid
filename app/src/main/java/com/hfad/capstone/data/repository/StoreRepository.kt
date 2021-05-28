package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.ProductDatabase
import com.hfad.capstone.data.database.db.StoreDatabase
import com.hfad.capstone.data.database.networkBoundResource
import javax.inject.Inject

class StoreRepository @Inject constructor(
        private val api: Api,
        private val db: StoreDatabase
) {
    private val storeDao = db.storeDao()

    fun getStore() = networkBoundResource(
            query = {
                storeDao.getAllStores()
            },
            fetch = {
                api.readStore()
            },
            saveFetchResult = { products ->
                db.withTransaction {
                    storeDao.deleteAllStores()
                    storeDao.insertStore(products.store)
                }
            }
    )
}