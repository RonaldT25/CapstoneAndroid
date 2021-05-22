package com.hfad.capstone.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.User
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sessionManager = SessionManager(this.requireContext())
        getProfile()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getProfile(){
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.getProfile(it).enqueue(object : Callback<User> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<User>, response: Response<User>) {
                binding.dashboardGreetings.text = binding.dashboardGreetings.text.toString()+ (response.body()?.username)
            }
            override fun onFailure(call: Call<User>, t: Throwable) {

            }

        })
        }
    }


}