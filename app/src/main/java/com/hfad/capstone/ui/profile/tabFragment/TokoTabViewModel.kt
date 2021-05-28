package com.hfad.capstone.ui.profile.tabFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.StoreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokoTabViewModel @Inject constructor(api : Api) : ViewModel() {
    private val storeLiveData = MutableLiveData<StoreResponse>()
    val store : LiveData<StoreResponse> = storeLiveData


    init {
        viewModelScope.launch {
            val store = api.readStore()
            storeLiveData.value = store
        }
    }

}