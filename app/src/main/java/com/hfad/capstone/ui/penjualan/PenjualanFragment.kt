package com.hfad.capstone.ui.penjualan

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
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.databinding.FragmentPenjualanBinding
import com.hfad.capstone.databinding.FragmentProdukBinding
import com.hfad.capstone.helper.*
import com.hfad.capstone.ui.dashboard.DashboardViewModel
import com.hfad.capstone.ui.detail.DetailActivity
import com.hfad.capstone.ui.detail.DetailTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PenjualanFragment : Fragment() {
    private var _binding: FragmentPenjualanBinding? = null
    private lateinit var viewModel: PenjualanViewModel
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
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.readTransaction().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { users -> getTransactions(users) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
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
        ).get(PenjualanViewModel::class.java)
    }

    private fun getTransactions(response : Response<List<Transaction>>) {
        val  listProduct = response.body()
        listProduct.let {
            transactionAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailTransaction::class.java)
                intent.putExtra(DetailTransaction.EXTRA_TRANSACTION, selectedData)
                startActivity(intent)
            }

            transactionAdapter.setData(listProduct)
            binding.progressBar.visibility = View.GONE
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