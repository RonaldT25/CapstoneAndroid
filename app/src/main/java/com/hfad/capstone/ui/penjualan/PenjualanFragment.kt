package com.hfad.capstone.ui.penjualan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.databinding.FragmentPenjualanBinding
import com.hfad.capstone.databinding.FragmentProdukBinding
import com.hfad.capstone.helper.ProdukAdapter
import com.hfad.capstone.helper.TransactionAdapter
import com.hfad.capstone.ui.detail.DetailActivity
import com.hfad.capstone.ui.detail.DetailTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PenjualanFragment : Fragment() {
    private var _binding: FragmentPenjualanBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPenjualanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientRetrofit = ClientRetrofit()
        getTransactions()
    }

    private fun getTransactions() {
        binding.progressBar.visibility = View.VISIBLE
        val transactionAdapter = TransactionAdapter()
        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).readTransaction() }
            if (response != null) {
                if (response.isSuccessful){
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