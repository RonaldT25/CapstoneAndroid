package com.hfad.capstone.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hfad.capstone.MainActivity
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.databinding.FragmentAddBahanBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddBahan : Fragment() {
    private var _binding: FragmentAddBahanBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBahanBinding.inflate(inflater, container, false)
        clientRetrofit = ClientRetrofit()
        binding.btnAddProduk.setOnClickListener {
            createComposition()
        }
        return binding.root
    }
    private fun createComposition(){

        GlobalScope.launch(Dispatchers.Main){
            val response = context?.let { clientRetrofit.getApiService(it).createComposition(binding.inputBahan.text.toString(), binding.satuanBahan.text.toString()) }
            if (response != null) {
                if(response.isSuccessful){
                    Toast.makeText(context, response.body()?.compositionName, Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}