package com.hfad.capstone.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.capstone.ui.dashboard.DashboardViewModel
import com.hfad.capstone.ui.penjualan.PenjualanViewModel
import com.hfad.capstone.ui.produk.tabFragment.BahanTabViewModel
import com.hfad.capstone.ui.produk.tabFragment.ProdukTabViewModel
import com.hfad.capstone.ui.profile.tabFragment.PenggunaTabViewModel
import com.hfad.capstone.ui.profile.tabFragment.TokoTabViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(Repository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(PenjualanViewModel::class.java)) {
            return PenjualanViewModel(Repository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(ProdukTabViewModel::class.java)) {
            return ProdukTabViewModel(Repository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(BahanTabViewModel::class.java)) {
            return BahanTabViewModel(Repository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(PenggunaTabViewModel::class.java)) {
            return PenggunaTabViewModel(Repository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(TokoTabViewModel::class.java)) {
            return TokoTabViewModel(Repository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}