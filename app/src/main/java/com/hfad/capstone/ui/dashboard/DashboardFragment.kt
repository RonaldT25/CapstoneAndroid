package com.hfad.capstone.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.User
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.helper.ApiHelper
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.helper.Status
import com.hfad.capstone.helper.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var viewModel: DashboardViewModel
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        clientRetrofit = ClientRetrofit()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getProfile().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { users -> getProfile(users) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                       binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            context?.let { clientRetrofit.getApiService(it) }?.let { ApiHelper(it) }?.let {
                ViewModelFactory(
                    it
                )
            }
        ).get(DashboardViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun getProfile(response:Response<User>){
        binding.dashboardGreetings.text = binding.dashboardGreetings.text.toString()+ (response.body()?.username)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}