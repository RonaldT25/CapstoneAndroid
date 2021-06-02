package com.hfad.capstone.ui.produk.tabFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.databinding.FragmentProdukTabBinding
import com.hfad.capstone.helper.Adapter.ProdukAdapter
import com.hfad.capstone.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProdukTab : Fragment() {
    private var _binding: FragmentProdukTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var produkAdapter: ProdukAdapter
    private val viewModel: ProdukTabViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProdukTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        produkAdapter = ProdukAdapter()
        setupObservers()
    }



    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer { result ->
            result.let { users -> result.data?.let { getProducts(it) } }
        })
    }

    private fun getProducts(response: List<Product>){
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