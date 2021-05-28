package com.hfad.capstone.ui.penjualan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.databinding.FragmentPenjualanBinding
import com.hfad.capstone.helper.DataMapper
import com.hfad.capstone.helper.TransactionAdapter
import com.hfad.capstone.ui.detail.DetailTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenjualanFragment : Fragment() {
    private var _binding: FragmentPenjualanBinding? = null
    private val viewModel: PenjualanViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    private lateinit var transactionAdapter:TransactionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPenjualanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientRetrofit = ClientRetrofit()
        transactionAdapter = TransactionAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
                result -> result.let { users -> result.data?.let { getTransactions(it) } }
            binding.progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
        })
    }



    private fun getTransactions(response : List<TransactionEntity>) {
        val  listProduct = response
        listProduct.let {
            transactionAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailTransaction::class.java)
                intent.putExtra(DetailTransaction.EXTRA_TRANSACTION, selectedData)
                startActivity(intent)
            }

            transactionAdapter.setData(DataMapper.mapEntitiesToDomain(listProduct))

            with(binding.rvTransaction) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = transactionAdapter
            }
    }}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}