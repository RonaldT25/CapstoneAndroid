package com.hfad.capstone.ui.profile.tabFragment

import androidx.lifecycle.*
import com.hfad.capstone.api.Api
import com.hfad.capstone.data.User
import com.hfad.capstone.data.repository.CompositionRepository
import com.hfad.capstone.helper.Repository
import com.hfad.capstone.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PenggunaTabViewModel @Inject constructor(api : Api) : ViewModel() {
    private val userLiveData = MutableLiveData<User>()
    val user : LiveData<User> = userLiveData


    init {
        viewModelScope.launch {
            val user = api.getProfile()
            userLiveData.value = user
        }
    }

}