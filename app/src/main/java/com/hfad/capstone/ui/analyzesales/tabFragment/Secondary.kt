package com.hfad.capstone.ui.analyzesales.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hfad.capstone.R
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.databinding.FragmentPrimaryBinding
import com.hfad.capstone.databinding.FragmentSecondaryBinding
import com.hfad.capstone.helper.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class Secondary : Fragment() {
    private var _binding: FragmentSecondaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SecondaryViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    var apiresponse : String =""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecondaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        setupObservers()
    }

    private fun setupObservers() {
        sessionManager.fetchProductId()?.let {
            viewModel.getReview(it.toInt()).observe(viewLifecycleOwner, Observer {
                    result -> result.let { users -> result.data?.let { getAnalyzeSales(it) } }
                binding.progressBar.isVisible = result is Resource.Loading
            })
        }
    }

    private fun getAnalyzeSales(responseSalesEntity: ResponseSalesEntity) {
        context?.let {
            Glide.with(it)
                .load(responseSalesEntity.start_next_6_months)
                .into(binding.secondary)
        }
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