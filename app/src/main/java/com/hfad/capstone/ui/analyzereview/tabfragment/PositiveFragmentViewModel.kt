package com.hfad.capstone.ui.analyzereview.tabfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ReviewResponseEntity
import com.hfad.capstone.data.repository.ReviewResponseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PositiveFragmentViewModel @Inject constructor(private val repository: ReviewResponseRepository) : ViewModel() {


    fun getReview(id:Int): LiveData<Resource<ReviewResponseEntity>> {
        return repository.getReview(id).asLiveData()
    }

}