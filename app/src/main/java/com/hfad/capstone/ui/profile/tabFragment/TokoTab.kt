package com.hfad.capstone.ui.profile.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.StoreResponse
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.FragmentTokoTabBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TokoTab : Fragment() {
    private var _binding: FragmentTokoTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTokoTabBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(this.requireContext())
        getStore()
        binding.btnLogin.setOnClickListener {
            updateStore()
        }
        return binding.root
    }

    private fun getStore() {
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.readStore(it).enqueue(object : Callback<StoreResponse> {
                override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                   binding.inputNama.setHint(response.body()?.store?.storeName)
                    binding.deskripsiToko.setHint(response.body()?.store?.description)
                }

                override fun onFailure(call: Call<StoreResponse>, t: Throwable) {

                }


            })
        }
    }

    private fun updateStore(){
        if (binding.inputNama.text.isEmpty()) {
            binding.inputNama.error = getText(R.string.usernamewarning)
            binding.inputNama.requestFocus()
        }
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.updateStore(it,
                binding.inputNama.text.toString(),
                binding.deskripsiToko.text.toString()
            ).enqueue(object : Callback<updateResponse> {
                override fun onResponse(call: Call<updateResponse>, response: Response<updateResponse>) {
                    Toast.makeText(context, response.body()?.message,LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<updateResponse>, t: Throwable) {

                }


            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}