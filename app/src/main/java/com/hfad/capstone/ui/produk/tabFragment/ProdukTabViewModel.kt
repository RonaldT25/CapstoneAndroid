package com.hfad.capstone.ui.produk.tabFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.hfad.capstone.data.repository.ProductRepository
import com.hfad.capstone.data.repository.TransactionRepository
import com.hfad.capstone.helper.Repository
import com.hfad.capstone.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProdukTabViewModel @Inject constructor(repository: ProductRepository) : ViewModel() {
    val products = repository.getProducts().asLiveData()
}