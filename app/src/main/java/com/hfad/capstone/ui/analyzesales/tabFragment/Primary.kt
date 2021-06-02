package com.hfad.capstone.ui.analyzesales.tabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.databinding.FragmentPrimaryBinding
import com.hfad.capstone.helper.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL


@AndroidEntryPoint
class Primary : Fragment() {
    private var _binding: FragmentPrimaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PrimaryViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private lateinit var clientRetrofit: ClientRetrofit
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
        val thread = Thread {
            try {
                apiresponse = URL(responseSalesEntity.compositions).readText()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()


        setTextView(apiresponse.replace("[","").replace("{","").replace("}","").replace("\u0022","").replace(",","\n"))

    }


    private fun setTextView(sales: String) {
        binding.textView.text = sales
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}