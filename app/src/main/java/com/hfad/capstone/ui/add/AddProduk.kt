package com.hfad.capstone.ui.add

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.User
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.FragmentAddProdukBinding
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddProduk : Fragment() {
    private var _binding: FragmentAddProdukBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddProdukBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(this.requireContext())
        binding.btnAddProduk.setOnClickListener {
            createProduct()
        }
        return binding.root
    }

    private fun createProduct(){
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.createProduct(it,
                binding.inputProduk.text.toString(),
                binding.hargaProduk.text.toString().toInt(),
                "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80"
            ).enqueue(object : Callback<Product> {
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    Toast.makeText(context, response.body()?.productName, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {

                }


            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}