package com.hfad.capstone.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.User
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.ActivityChangePasswordBinding
import com.hfad.capstone.databinding.ActivityDetailBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }
        setContentView(binding.root)
    }

    private fun changePassword() {
        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.updateProfilePassword(it,
            binding.inputSandi.text.toString()).enqueue(object : Callback<updateResponse> {
                override fun onResponse(call: Call<updateResponse>, response: Response<updateResponse>) {
                    Toast.makeText(this@ChangePasswordActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ChangePasswordActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<updateResponse>, t: Throwable) {

                }


            })
        }
    }


}