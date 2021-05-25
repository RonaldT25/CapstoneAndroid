package com.hfad.capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Composition
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.ActivityDetailCompostionBinding
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailComposition : AppCompatActivity() {
    companion object{
        const val EXTRA_COMPOSITION = "extra-composition"
    }
    private lateinit var binding: ActivityDetailCompostionBinding
    private var extras: Composition? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCompostionBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
        supportActionBar?.title = getString(R.string.detailComposition)
        setContentView(binding.root)
        setDetail()
    }

    private fun setDetail() {
        extras = intent.getParcelableExtra(EXTRA_COMPOSITION)
        if (extras!=null){
            binding.detailCompositionTitle.text = extras!!.compositionName
            binding.editTextUnit.setHint(extras!!.unit)
        }
        binding.btnSave.setOnClickListener{
            update()
        }
    }

    private fun update() {
        if (binding.editTextUnit.text.isEmpty()) {
            binding.editTextUnit.error = getText(R.string.usernamewarning)
            binding.editTextUnit.requestFocus()
        }
        else{
            sessionManager.fetchAuthToken()?.let {
                extras?.let { it1 ->
                    ClientRetrofit.instanceRetrofit.updateComposition(
                        it1.id,
                        it1.compositionName,
                        binding.editTextUnit.text.toString(),
                        it
                    ).enqueue(object : Callback<updateResponse> {
                        override fun onResponse(call: Call<updateResponse>, response: Response<updateResponse>) {
                            Toast.makeText(this@DetailComposition, response.body()?.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailComposition, MainActivity::class.java)
                            startActivity(intent)
                        }

                        override fun onFailure(call: Call<updateResponse>, t: Throwable) {

                        }

                    })
                }
            }
        }

    }
}