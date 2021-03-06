package com.hfad.capstone.ui.auth


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.UserActivity
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.ResponseAuth
import com.hfad.capstone.data.model.User
import com.hfad.capstone.databinding.ActivityLoginBinding
import com.hfad.capstone.helper.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clientRetrofit = ClientRetrofit()
        sessionManager = SessionManager(this)
        checkToken()
        binding.forgotPassword.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            login()
        }
    }

    private fun login(){
        binding.progressBar.visibility = View.VISIBLE
        if (binding.editTextLogin.text.isEmpty()) {
            binding.editTextLogin.error = getText(R.string.usernamewarning)
            binding.editTextLogin.requestFocus()
        }
        if (binding.editTextPassword.text?.isEmpty() == true) {
            binding.editTextPassword.error = getText(R.string.passwordwarning)
            binding.editTextPassword.requestFocus()
        }

        else{
            clientRetrofit.getApiService(this).login(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString()
            ).enqueue(object : Callback<ResponseAuth> {
                override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                    if (response.isSuccessful){
                        binding.progressBar.visibility = View.GONE
                        response.body()?.let { sessionManager.saveAuthToken(it.token) }
                        loginSuccess()
                    }
                    else{
                        loginFailed()
                    }
                }

                override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {

                }

            })
        }
    }
    @SuppressLint("ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables")
    private fun loginSuccess() {
        this.setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Login")
            setMessage(getText(R.string.login_success))
            setPositiveButton("OK", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    clientRetrofit.getApiService(this@Login).getProfilePublic().enqueue(object : Callback<User>{
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.body()?.role == "seller"){
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else if (response.body()?.role == "user"){
                                val intent = Intent(this@Login, UserActivity::class.java)
                                startActivity(intent)
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {

                        }

                    })
                }
            })
            setIcon(resources.getDrawable(R.drawable.check, theme))
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    @SuppressLint("ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables")
    private fun loginFailed() {
        this.setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        val builder = AlertDialog.Builder(this@Login)
        with(builder) {
            setTitle("Login")
            setMessage(getText(R.string.login_failed))
            setPositiveButton("OK", null)
            setIcon(resources.getDrawable(R.drawable.failed, theme))
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun checkToken(){
        if (sessionManager.fetchAuthToken() != null)
        {
            binding.progressBar.visibility = View.VISIBLE
        }

        sessionManager.fetchAuthToken()?.let {
            clientRetrofit.getApiService(this).checkToken(it).enqueue(object : Callback<ResponseAuth> {
                override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                    binding.progressBar.visibility = View.GONE
                    if (response.body()?.auth == true){
                        clientRetrofit.getApiService(this@Login).getProfilePublic().enqueue(object : Callback<User>{
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.body()?.role == "seller"){
                                    val intent = Intent(this@Login, MainActivity::class.java)
                                    startActivity(intent)
                                }
                                else if (response.body()?.role == "user"){
                                    val intent = Intent(this@Login, UserActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {

                            }

                        })
                    }

                }

                override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {

                }


            })
        }
    }



}