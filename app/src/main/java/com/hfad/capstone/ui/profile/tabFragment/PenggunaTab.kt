package com.hfad.capstone.ui.profile.tabFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.User
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.FragmentBahanTabBinding
import com.hfad.capstone.databinding.FragmentPenggunaTabBinding
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.ChangePasswordActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PenggunaTab : Fragment() {
    private var _binding: FragmentPenggunaTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPenggunaTabBinding.inflate(inflater, container, false)
        clientRetrofit = ClientRetrofit()
        getProfile()
        binding.btnLogin.setOnClickListener {
            updateProfile()
        }
        binding.changePassword.setOnClickListener {
            val intent = Intent(context, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }

    private fun getProfile() {

        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).getProfile() }
            if (response != null) {
                if (response.isSuccessful){
                    binding.inputNama.setHint(response.body()?.username)
                    binding.email.setHint(response.body()?.email)
                }
            }
        }
    }

    private fun updateProfile(){
        if (binding.inputNama.text.isEmpty()) {
            binding.inputNama.error = getText(R.string.usernamewarning)
            binding.inputNama.requestFocus()
        }
        if (binding.email.text.isEmpty()) {
            binding.email.error = getText(R.string.emailwarning)
            binding.email.requestFocus()
        }
        if (!binding.email.text.isValidEmail() && binding.email.text.isNotEmpty()) {
            binding.email.error = getText(R.string.emailnotvalidwarning)
            binding.email.requestFocus()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).updateProfile(binding.inputNama.text.toString(),
                binding.email.text.toString()) }
            if (response != null) {
                if (response.isSuccessful){
                    Toast.makeText(context, response.body()?.message,LENGTH_SHORT).show()
                }
            }
        }
    }
    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}