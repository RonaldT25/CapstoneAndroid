package com.hfad.capstone.data.repository

import androidx.room.withTransaction
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.database.db.ProductDatabase
import com.hfad.capstone.data.database.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: Api,
    private val db: ProductDatabase
) {
    private val productDao = db.productDao()

    fun getProducts() = networkBoundResource(
        query = {
            productDao.getAllProducts()
        },
        fetch = {
            api.readProduct()
        },
        saveFetchResult = { products ->
            db.withTransaction {
                productDao.deleteAllProducts()
                productDao.insertProducts(products)
            }
        }
    )

    fun search(searchQuery:String) : Flow<Int> {
        return productDao.search(searchQuery)
    }

    fun getProductsPublic() = api.readProductPublic()
}