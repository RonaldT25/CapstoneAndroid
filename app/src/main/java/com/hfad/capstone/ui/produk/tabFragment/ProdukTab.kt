package com.hfad.capstone.ui.produk.tabFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Product
import com.hfad.capstone.databinding.FragmentProdukTabBinding
import com.hfad.capstone.helper.ProdukAdapter
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukTab : Fragment() {
    private var _binding: FragmentProdukTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProdukTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientRetrofit = ClientRetrofit()
        getProducts()
    }
    private fun getProducts(){

        val produkAdapter = ProdukAdapter()
        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).readProduct() }
            if (response != null) {
                if (response.isSuccessful){
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
                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}