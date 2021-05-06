package com.hfad.capstone.ui.auth


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hfad.capstone.R
import com.hfad.capstone.databinding.ActivityLoginBinding

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
    }




}