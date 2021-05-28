package com.hfad.capstone.ui.penjualan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.databinding.FragmentPenjualanBinding
import com.hfad.capstone.helper.Adapter.TransactionAdapter
import com.hfad.capstone.helper.DataMapper
import com.hfad.capstone.ui.detail.DetailTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenjualanFragment : Fragment() {
    private var _binding: FragmentPenjualanBinding? = null
    private val viewModel: PenjualanViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var transactionAdapter: TransactionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPenjualanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAdapter = TransactionAdapter()
        setupObservers()
        searching(binding.searchView)
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    search(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!=null){
                    search(query)
                }
                return true
            }
        })
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



    private fun search(query:String){
        val searchQuery = "%$query%"
        viewModel.search(searchQuery).observe(this,{ list ->
            list.let {
                transactionAdapter.setData(DataMapper.mapEntitiesToDomain(it))
            }
        })
    }
}