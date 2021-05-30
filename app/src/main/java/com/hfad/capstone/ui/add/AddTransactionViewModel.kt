package com.hfad.capstone.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.database.CompositionDetailEntity
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.repository.CompositionDetailRepository
import com.hfad.capstone.data.repository.CompositionRepository
import com.hfad.capstone.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val products = repository.getProducts().asLiveData()
    fun search(searchQuery:String): LiveData<Int>{
        return repository.search(searchQuery).asLiveData()
    }
}