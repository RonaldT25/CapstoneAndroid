package com.hfad.capstone.ui.analyzesales.tabFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.data.repository.ResponseSalesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondaryViewModel @Inject constructor(private val repository: ResponseSalesRepository) : ViewModel() {
    fun getReview(id:Int): LiveData<Resource<ResponseSalesEntity>> {
        return repository.getReviewFromDb(id).asLiveData()
    }
}