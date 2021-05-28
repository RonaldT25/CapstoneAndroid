package com.hfad.capstone.ui.penjualan

import androidx.lifecycle.*
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PenjualanViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {
    val transactions = repository.getTransactions().asLiveData()

    fun search(searchQuery:String): LiveData<List<TransactionEntity>>{
       return repository.search(searchQuery).asLiveData()
    }

}