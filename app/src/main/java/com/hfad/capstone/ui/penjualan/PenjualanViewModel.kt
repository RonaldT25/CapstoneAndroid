package com.hfad.capstone.ui.penjualan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hfad.capstone.helper.Repository
import com.hfad.capstone.helper.Resource
import kotlinx.coroutines.Dispatchers

class PenjualanViewModel(private val repository: Repository) : ViewModel() {
    fun readTransaction() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.readTransaction()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}