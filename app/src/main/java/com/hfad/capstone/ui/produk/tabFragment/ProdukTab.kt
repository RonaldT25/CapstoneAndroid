package com.hfad.capstone.ui.produk.tabFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.databinding.FragmentProdukTabBinding
import com.hfad.capstone.helper.*
import com.hfad.capstone.ui.detail.DetailActivity
import com.hfad.capstone.ui.penjualan.PenjualanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ProdukTab : Fragment() {
    private var _binding: FragmentProdukTabBinding? = null
    private val viewModel: ProdukTabViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    private lateinit var produkAdapter: ProdukAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProdukTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientRetrofit = ClientRetrofit()
        produkAdapter = ProdukAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
                result -> result.let { users -> result.data?.let { getProducts(it) } }
        })
    }

    private fun getProducts(response : List<Product>){
        val  listProduct = response
        listProduct.let {
            produkAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_PRODUCT, selectedData)
                startActivity(intent)
            }

            produkAdapter.setData(listProduct)

            with(binding.rvProduct) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = produkAdapter
            }
    }}



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}