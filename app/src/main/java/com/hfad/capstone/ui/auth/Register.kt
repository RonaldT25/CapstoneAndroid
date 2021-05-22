package com.hfad.capstone.ui.auth

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.ResponseAuth
import com.hfad.capstone.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    var role = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        binding.role.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.seller) {
                role = "seller"
            }
            if (checkedId == R.id.user) {
                role = "user"
            }
        }

        binding.btnRegister.setOnClickListener {
            register()
        }
    }
    private fun register() {
        if (binding.editTextName.text.isEmpty()) {
            binding.editTextName.error = getText(R.string.usernamewarning)
            binding.editTextName.requestFocus()
        }
        if (binding.editTextEmail.text.isEmpty()) {
            binding.editTextEmail.error = getText(R.string.emailwarning)
            binding.editTextEmail.requestFocus()
        }
        if (binding.editTextPassword.text?.isEmpty() == true) {
            binding.editTextPassword.error = getText(R.string.passwordwarning)
            binding.editTextPassword.requestFocus()
        }
        if (!binding.editTextEmail.text.isValidEmail() && binding.editTextEmail.text.isNotEmpty()) {
            binding.editTextEmail.error = getText(R.string.emailnotvalidwarning)
            binding.editTextEmail.requestFocus()
        }
        if (role.isEmpty()) {
            Toast.makeText(this, "Role Harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            ClientRetrofit.instanceRetrofit.register(
                binding.editTextName.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString(),
                role
            ).enqueue(object : Callback<ResponseAuth> {
                override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                    if (response.isSuccessful){
                            registerSuccess()
                        }
                    else{
                            registerFailed()
                        }
                }

                override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {

                }
            })
        }

    }

    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    @SuppressLint("ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables")
    private fun registerSuccess() {
        this.setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Register")
            setMessage(getText(R.string.register_success))
            setPositiveButton("OK", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val intent = Intent(this@Register, Login::class.java)
                    startActivity(intent)
                }
            })
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