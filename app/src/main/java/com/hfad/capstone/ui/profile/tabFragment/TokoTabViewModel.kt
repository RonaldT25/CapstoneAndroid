package com.hfad.capstone.ui.profile.tabFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hfad.capstone.helper.Repository
import com.hfad.capstone.helper.Resource
import kotlinx.coroutines.Dispatchers

class TokoTabViewModel(private val repository: Repository) : ViewModel() {
    fun readStore() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.readStore()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}