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
import com.hfad.capstone.data.Composition
import com.hfad.capstone.data.Product
import com.hfad.capstone.databinding.FragmentAddBahanBinding
import com.hfad.capstone.databinding.FragmentAddProdukBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddBahan : Fragment() {
    private var _binding: FragmentAddBahanBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBahanBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(this.requireContext())
        binding.btnAddProduk.setOnClickListener {
            createComposition()
        }
        return binding.root
    }
    private fun createComposition(){
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.createComposition(it,
                binding.inputBahan.text.toString(),
                binding.satuanBahan.text.toString()).enqueue(object : Callback<Composition> {
                override fun onResponse(call: Call<Composition>, response: Response<Composition>) {
                    Toast.makeText(context, response.body()?.compositionName, Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<Composition>, t: Throwable) {

                }


            })
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}