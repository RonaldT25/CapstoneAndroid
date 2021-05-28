package com.hfad.capstone.ui.produk.tabFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hfad.capstone.data.repository.CompositionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BahanTabViewModel @Inject constructor(repository: CompositionRepository) : ViewModel() {
    val compositions = repository.getCompositions().asLiveData()
}