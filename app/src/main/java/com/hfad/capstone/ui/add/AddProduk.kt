package com.hfad.capstone.ui.add

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hfad.capstone.MainActivity
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.databinding.FragmentAddProdukBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddProduk : Fragment() {
    private var _binding: FragmentAddProdukBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddProdukBinding.inflate(inflater, container, false)
       clientRetrofit = ClientRetrofit()
        binding.btnAddProduk.setOnClickListener {
            createProduct()
        }
        return binding.root
    }

    private fun createProduct(){
        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).createProduct(binding.inputProduk.text.toString(),
                binding.hargaProduk.text.toString().toInt(),
                "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80") }
            if (response != null) {
                if (response.isSuccessful){
                    Toast.makeText(context, response.body()?.productName, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}