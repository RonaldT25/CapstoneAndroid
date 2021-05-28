package com.hfad.capstone.ui.profile.tabFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokoTabViewModel @Inject constructor(repository: StoreRepository) : ViewModel() {
    val store = repository.getStore().asLiveData()
}