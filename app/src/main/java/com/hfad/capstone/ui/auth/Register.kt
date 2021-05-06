package com.hfad.capstone.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.capstone.R
import com.hfad.capstone.databinding.ActivityLoginBinding
import com.hfad.capstone.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
    }
}