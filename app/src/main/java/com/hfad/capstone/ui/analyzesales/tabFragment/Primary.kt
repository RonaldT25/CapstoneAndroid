package com.hfad.capstone.ui.analyzesales.tabFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.data.model.Sales
import com.hfad.capstone.databinding.FragmentPrimaryBinding
import com.hfad.capstone.helper.Adapter.ProdukAdapter
import com.hfad.capstone.helper.Adapter.SalesAdapter
import com.hfad.capstone.helper.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@AndroidEntryPoint
class Primary : Fragment() {
    private var _binding: FragmentPrimaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PrimaryViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private lateinit var clientRetrofit: ClientRetrofit
    private lateinit var salesAdapter: SalesAdapter
    var apiresponse : String =""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrimaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        clientRetrofit = ClientRetrofit()
        salesAdapter = SalesAdapter()
        setupObservers()

    }

    private fun setupObservers() {
        sessionManager.fetchProductId()?.let {
            viewModel.getAnalyzeSales(it.toInt()).observe(viewLifecycleOwner, Observer { result ->
                result.let { users ->
                    result.data?.let {
                        getAnalyzeSales(
                            it
                        )
                    }
                }
                binding.progressBar.isVisible = result is Resource.Loading
                result.data?.let { it1 -> setCompositionPrediction(it1) }
            })
        }
    }

    private fun getAnalyzeSales(responseSalesEntity: ResponseSalesEntity) {
        context?.let {
            Glide.with(it)
                .load(responseSalesEntity.next_6_months)
                .into(binding.primary)
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

            with(binding.rvPrimary) {
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