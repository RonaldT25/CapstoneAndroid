package com.hfad.capstone.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.User
import com.hfad.capstone.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashboardGreetings.text = ""
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            result -> result.let { users -> users.data?.let { getProfile(it) } }
        })
    }



    @SuppressLint("SetTextI18n")
    private fun getProfile(response:User){
        binding.dashboardGreetings.text = getString(R.string.example) + (response.username)
    }
    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

}