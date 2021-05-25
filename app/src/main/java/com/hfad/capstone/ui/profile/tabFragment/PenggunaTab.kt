package com.hfad.capstone.ui.profile.tabFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.User
import com.hfad.capstone.databinding.FragmentBahanTabBinding
import com.hfad.capstone.databinding.FragmentPenggunaTabBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PenggunaTab : Fragment() {
    private var _binding: FragmentPenggunaTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPenggunaTabBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(this.requireContext())
        getProfile()
        return binding.root

    }

    private fun getProfile() {
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.getProfile(it).enqueue(object : Callback<User> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    binding.inputNama.setHint(response.body()?.username)
                    binding.email.setHint(response.body()?.email)
                }
                override fun onFailure(call: Call<User>, t: Throwable) {

                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}