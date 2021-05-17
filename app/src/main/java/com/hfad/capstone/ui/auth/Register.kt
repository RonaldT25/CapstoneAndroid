package com.hfad.capstone.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
    @SuppressLint("ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables")
    private fun registerSuccess() {
        this.setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Register")
            setMessage(getText(R.string.register_success))
            setPositiveButton("OK", null)
            setIcon(resources.getDrawable(R.drawable.check, theme))
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    @SuppressLint("ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables")
    private fun registerFailed() {
        this.setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Register")
            setMessage(getText(R.string.register_failed))
            setPositiveButton("OK", null)
            setIcon(resources.getDrawable(R.drawable.failed, theme))
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}