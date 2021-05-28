package com.hfad.capstone.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(api : Api) : ViewModel() {
        private val userLiveData = MutableLiveData<User>()
        val user : LiveData<User> = userLiveData


    init {
        viewModelScope.launch {
             val user = api.getProfile()
            userLiveData.value = user
        }
    }

}