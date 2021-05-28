package com.hfad.capstone.ui.profile.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.StoreResponse
import com.hfad.capstone.databinding.FragmentTokoTabBinding
import com.hfad.capstone.helper.ApiHelper
import com.hfad.capstone.helper.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class TokoTab : Fragment() {
    private var _binding: FragmentTokoTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    private  val viewModel: TokoTabViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTokoTabBinding.inflate(inflater, container, false)
        clientRetrofit = ClientRetrofit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.btnLogin.setOnClickListener {
            updateStore()
        }
    }

    private fun setupObservers() {
        viewModel.store.observe(viewLifecycleOwner, Observer {
                result -> result.let { users -> result.let { getStore(it) } }
        })
    }





    private fun getStore(response:StoreResponse) {
        binding.inputNama.setHint(response.store.storeName)
        binding.deskripsiToko.setHint(response.store.description)
    }

    private fun updateStore(){
        if (binding.inputNama.text.isEmpty()) {
            binding.inputNama.error = getText(R.string.usernamewarning)
            binding.inputNama.requestFocus()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).updateStore( binding.inputNama.text.toString(),
                binding.deskripsiToko.text.toString()) }
            if (response != null) {
                if (response.isSuccessful){
                    Toast.makeText(context, response.body()?.message,LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}