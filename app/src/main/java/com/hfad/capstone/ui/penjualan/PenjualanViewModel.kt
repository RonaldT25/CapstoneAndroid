package com.hfad.capstone.ui.penjualan

import androidx.lifecycle.*
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.data.repository.TransactionRepository
import com.hfad.capstone.helper.Repository
import com.hfad.capstone.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PenjualanViewModel @Inject constructor(repository: TransactionRepository) : ViewModel() {
    val transactions = repository.getTransactions().asLiveData()
}