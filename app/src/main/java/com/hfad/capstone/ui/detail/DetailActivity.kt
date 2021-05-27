package com.hfad.capstone.ui.detail


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.ActivityDetailBinding
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.analyzereview.AnalyzeReviewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT = "extra-product"
    }

    private lateinit var binding: ActivityDetailBinding
    private var extras: Product? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        clientRetrofit = ClientRetrofit()
        sessionManager = SessionManager(this)
        supportActionBar?.title = getString(R.string.detail)
        setContentView(binding.root)
        setDetail()
    }

    private fun setDetail() {
        extras = intent.getParcelableExtra(EXTRA_PRODUCT)
        sessionManager.saveProductId(extras?.id.toString())
        if (extras != null) {
            binding.detailTitle.text = extras!!.productName
            binding.editTextHarga.setHint(extras!!.price.toString())
            binding.editTextTautan.setHint(extras!!.tokopediaProductUrl.toString())
        }
        binding.btnSave.setOnClickListener {
            update()
        }
        binding.deleteProduct.setOnClickListener {
            delete()
        }
        binding.buttonAnalisaUlasan.setOnClickListener {
            analyze()
        }
    }


    private fun update() {
        if (binding.editTextHarga.text.isEmpty()) {
            binding.editTextHarga.error = getText(R.string.usernamewarning)
            binding.editTextHarga.requestFocus()
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                extras?.let { it1 ->
                    val response = clientRetrofit.getApiService(this@DetailActivity).updateProduct(
                        it1.id,
                        it1.productName,
                        binding.editTextHarga.text.toString().toInt()
                    )
                        if (response.isSuccessful) {
                            Toast.makeText(this@DetailActivity, response.body()?.message, LENGTH_SHORT).show()
                            val intent = Intent(this@DetailActivity, MainActivity::class.java)
                            startActivity(intent)
                    }
                }
            }
        }
    }

    private fun delete() {
        GlobalScope.launch(Dispatchers.Main) {
            extras?.let { it1 ->
                val response = clientRetrofit.getApiService(this@DetailActivity).deleteProduct(it1.id)
                if (response.isSuccessful) {
                    Toast.makeText(this@DetailActivity, response.body()?.message, LENGTH_SHORT).show()
                    val intent = Intent(this@DetailActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun analyze() {
        val intent = Intent(this@DetailActivity, AnalyzeReviewActivity::class.java)
        startActivity(intent)
    }
}