package com.hfad.capstone.ui.produk.tabFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.databinding.FragmentProdukTabBinding
import com.hfad.capstone.helper.*
import com.hfad.capstone.ui.detail.DetailActivity
import com.hfad.capstone.ui.penjualan.PenjualanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukTab : Fragment() {
    private var _binding: FragmentProdukTabBinding? = null
    private lateinit var viewModel: ProdukTabViewModel
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
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.readProduct().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users -> getProducts(users) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            context?.let { clientRetrofit.getApiService(it) }?.let { ApiHelper(it) }?.let {
                ViewModelFactory(
                    it
                )
            }
        ).get(ProdukTabViewModel::class.java)
    }

    private fun getProducts(response : Response<List<Product>>){
        val  listProduct = response.body()!!
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