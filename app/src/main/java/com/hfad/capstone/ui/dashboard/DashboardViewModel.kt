package com.hfad.capstone.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hfad.capstone.helper.Resource
import com.hfad.capstone.helper.Repository
import kotlinx.coroutines.Dispatchers

class DashboardViewModel(private val repository: Repository) : ViewModel() {
    fun getProfile() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getProfile()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}