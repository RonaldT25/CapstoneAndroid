package com.hfad.capstone.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.Composition
import com.hfad.capstone.databinding.ActivityDetailCompostionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailComposition : AppCompatActivity() {
    companion object{
        const val EXTRA_COMPOSITION = "extra-composition"
    }
    private lateinit var binding: ActivityDetailCompostionBinding
    private var extras: Composition? = null
    private lateinit var clientRetrofit: ClientRetrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCompostionBinding.inflate(layoutInflater)
        clientRetrofit = ClientRetrofit()
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
        binding.deleteComposition.setOnClickListener {
            delete()
        }
    }

    private fun update() {
        if (binding.editTextUnit.text.isEmpty()) {
            binding.editTextUnit.error = getText(R.string.usernamewarning)
            binding.editTextUnit.requestFocus()
        }
        else{
            GlobalScope.launch(Dispatchers.Main) {
                extras?.let { it1 ->
                    val response = clientRetrofit.getApiService(this@DetailComposition).updateComposition(
                        it1.compositionId,
                        it1.compositionName,
                        binding.editTextUnit.text.toString()
                    )
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailComposition, response.body()?.message, Toast.LENGTH_SHORT).show()
                        this@DetailComposition.finish()
                    }
                }
            }
        }
    }

    private fun delete(){
        GlobalScope.launch(Dispatchers.Main) {
            extras?.let { it1 ->
                val response = clientRetrofit.getApiService(this@DetailComposition).deleteComposition(it1.compositionId)
                if (response.isSuccessful) {
                    Toast.makeText(this@DetailComposition, response.body()?.message, Toast.LENGTH_SHORT).show()
                    this@DetailComposition.finish()
                }
            }
        }
    }
}