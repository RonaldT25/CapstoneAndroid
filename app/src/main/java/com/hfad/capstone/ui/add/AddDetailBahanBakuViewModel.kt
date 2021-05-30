package com.hfad.capstone.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.database.CompositionDetailEntity
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.repository.CompositionDetailRepository
import com.hfad.capstone.data.repository.CompositionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddDetailBahanBakuViewModel @Inject constructor(private val repository: CompositionDetailRepository
,private val repository2: CompositionRepository) : ViewModel() {

    fun getCompositionDetail(id:Int): LiveData<Resource<List<CompositionDetailEntity>>> {
        return repository.getCompositionsDetail(id).asLiveData()
    }
    val compositions = repository2.getCompositions().asLiveData()
    fun search(searchQuery:String): LiveData<Int>{
        return repository2.search(searchQuery).asLiveData()
    }
}