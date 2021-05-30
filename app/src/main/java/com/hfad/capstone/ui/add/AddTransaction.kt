package com.hfad.capstone.ui.add

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hfad.capstone.MainActivity
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.databinding.FragmentAddTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddTransaction : Fragment() {
    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<String>()
    private lateinit var clientRetrofit: ClientRetrofit
    private val viewModel: AddTransactionViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientRetrofit = ClientRetrofit()
        setupObservers()
        setButtons()
    }

    private fun setButtons() {
        binding.btnAddProduk.setOnClickListener {
            viewModel.search(binding.ACTextView.text.toString()).observe(viewLifecycleOwner, { list ->
                list.let {
                    if (list != 0) {
                        GlobalScope.launch(Dispatchers.Main) {
                                clientRetrofit.getApiService(requireContext()).insertTransaction(
                                        list,
                                        list,
                                        Calendar.getInstance().time.toString(),
                                        binding.hargaProduk.text.toString().toInt()
                                )
                        }
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer { result ->
            result.let { users -> result.data?.let { getProducts(it) } }
        })
        setSpinner()
    }

    private fun setSpinner() {
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, list)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.ACTextView.setAdapter(arrayAdapter)
    }

    private fun getProducts(product: List<Product>) {
        list.clear()
        for(name in product){
            list.add(name.productName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}