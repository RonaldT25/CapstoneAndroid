package com.hfad.capstone.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.data.model.User
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.helper.Adapter.ProdukAdapter
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var produkAdapter: ProdukAdapter
    private lateinit var clientRetrofit : ClientRetrofit
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashboardGreetings.text = ""
        produkAdapter = ProdukAdapter()
        clientRetrofit = ClientRetrofit()
        setupObservers()
    }

    private fun setupObservers() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.user.observe(viewLifecycleOwner, Observer { result ->
            result.let { users -> users.data?.let { getProfile(it) } }
        })
        clientRetrofit.getApiService(requireContext()).readProductPublic().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                response.body()?.let { getProducts(it) }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

            }

        })
    }
    private fun getProducts(response: List<Product>){
        val  listProduct = response

        listProduct.let {
            produkAdapter.onItemClick = { selectedData ->
                if (selectedData.tokopediaProductId != null || selectedData.tokopediaProductUrl != ""){
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedData.tokopediaProductUrl))
                    startActivity(browserIntent)
                }
                else{
                    val browserIntent2 = Intent(Intent.ACTION_VIEW, Uri.parse("https://tokolitik.tech/product/"+selectedData.id.toString()))
                    startActivity(browserIntent2)
            }
            }

            produkAdapter.setData(listProduct)

            with(binding.rvDashboard) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = produkAdapter
            }
        }}



    @SuppressLint("SetTextI18n")
    private fun getProfile(response: User){
        binding.dashboardGreetings.text = getString(R.string.example) + (response.username)
    }
    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

}