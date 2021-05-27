package com.hfad.capstone.ui.produk.tabFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hfad.capstone.helper.Repository
import com.hfad.capstone.helper.Resource
import kotlinx.coroutines.Dispatchers

class ProdukTabViewModel(private val repository: Repository) : ViewModel() {
    fun readProduct() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.readProduct()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}