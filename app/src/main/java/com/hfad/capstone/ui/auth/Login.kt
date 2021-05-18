package com.hfad.capstone.ui.auth


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.hfad.capstone.API.ClientRetrofit
import com.hfad.capstone.R
import com.hfad.capstone.data.ResponseAuth
import com.hfad.capstone.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            login()
        }
    }

    private fun login(){
        if (binding.editTextLogin.text.isEmpty()) {
            binding.editTextLogin.error = getText(R.string.usernamewarning)
            binding.editTextLogin.requestFocus()
        }
        if (binding.editTextPassword.text.isEmpty()) {
            binding.editTextPassword.error = getText(R.string.passwordwarning)
            binding.editTextPassword.requestFocus()
        }

        else{
            ClientRetrofit.instanceRetrofit.login(
                    binding.editTextLogin.text.toString(),
                    binding.editTextPassword.text.toString()
            ).enqueue(object : Callback<ResponseAuth> {
                override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                    if (response.isSuccessful){

                    }
                }

                override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {

                }

            })
        }
    }




}