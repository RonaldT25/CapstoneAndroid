package com.hfad.capstone.ui.analyzesales.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.data.model.Sales
import com.hfad.capstone.databinding.FragmentPrimaryBinding
import com.hfad.capstone.databinding.FragmentSecondaryBinding
import com.hfad.capstone.helper.Adapter.SalesAdapter
import com.hfad.capstone.helper.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

@AndroidEntryPoint
class Secondary : Fragment() {
    private var _binding: FragmentSecondaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SecondaryViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    var apiresponse : String =""
    private lateinit var salesAdapter: SalesAdapter
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecondaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        salesAdapter = SalesAdapter()
        clientRetrofit = ClientRetrofit()
        setupObservers()
    }

    private fun setupObservers() {
        sessionManager.fetchProductId()?.let {
            viewModel.getReview(it.toInt()).observe(viewLifecycleOwner, Observer {
                    result -> result.let { users -> result.data?.let { getAnalyzeSales(it) } }
                binding.progressBar.isVisible = result is Resource.Loading
                result.data?.let { it1 -> setCompositionPrediction(it1) }
            })
        }
    }

    private fun getAnalyzeSales(responseSalesEntity: ResponseSalesEntity) {
        context?.let {
            Glide.with(it)
                .load(responseSalesEntity.start_next_6_months)
                .into(binding.secondary)
        }
        setCompositionPrediction(responseSalesEntity)
    }

    private fun setCompositionPrediction(responseSalesEntity: ResponseSalesEntity){
        clientRetrofit.getApiService(requireContext()).readSales(responseSalesEntity.compositions.replace("http://tokolitik.tech:3000/",""))
                .enqueue(object : Callback<List<Sales>> {
                    override fun onResponse(call: Call<List<Sales>>, response: Response<List<Sales>>) {
                        response.body()?.let { getSales(it) }

                    }

                    override fun onFailure(call: Call<List<Sales>>, t: Throwable) {

                    }


                })
    }

    private fun getSales(response: List<Sales>){
        val  listProduct = response

        listProduct.let {
            salesAdapter.onItemClick = { selectedData ->

            }

            salesAdapter.setData(listProduct)

            with(binding.rvSecondary) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = salesAdapter
            }
        }}



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}