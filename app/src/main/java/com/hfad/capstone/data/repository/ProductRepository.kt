package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.ProductDatabase
import com.hfad.capstone.data.database.networkBoundResource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: Api,
    private val db: ProductDatabase
) {
    private val transactionDao = db.productDao()

    fun getProducts() = networkBoundResource(
        query = {
            transactionDao.getAllProducts()
        },
        fetch = {
            api.readProduct()
        },
        saveFetchResult = { products ->
            db.withTransaction {
                transactionDao.deleteAllProducts()
                transactionDao.insertProducts(products)
            }
        }
    )
}