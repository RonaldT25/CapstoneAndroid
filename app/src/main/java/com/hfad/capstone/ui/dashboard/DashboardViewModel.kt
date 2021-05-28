package com.hfad.capstone.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(repository: UserRepository) : ViewModel() {
    val user = repository.getProfile().asLiveData()
}