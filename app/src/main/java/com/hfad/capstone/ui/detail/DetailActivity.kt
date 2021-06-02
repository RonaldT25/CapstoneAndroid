package com.hfad.capstone.ui.detail


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.databinding.ActivityDetailBinding
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.add.AddActivity
import com.hfad.capstone.ui.analyzereview.AnalyzeReviewActivity
import com.hfad.capstone.ui.analyzesales.AnalyzeSalesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT = "extra-product"
    }
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
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
        binding.buttonDetailBahanBaku.setOnClickListener {
            addDetailBahanBaku()
        }
        binding.deleteProduct.setOnClickListener {
            delete()
        }
        binding.buttonAnalisaUlasan.setOnClickListener {
            analyze()
        }
       binding.buttonAnalisaPenjualan.setOnClickListener{
           analyzeSales()
       }
    }

    private fun analyzeSales(){
        val intent = Intent(this@DetailActivity, AnalyzeSalesActivity::class.java)
        startActivity(intent)
    }

    private fun addDetailBahanBaku() {
        extras = intent.getParcelableExtra(EXTRA_PRODUCT)
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra(AddActivity.EXTRA_ID, 3)
        startActivity(intent)
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
                            this@DetailActivity.finish()
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
                    this@DetailActivity.finish()
                }
            }
        }
    }

    private fun analyze() {
        val intent = Intent(this@DetailActivity, AnalyzeReviewActivity::class.java)
        startActivity(intent)
    }
}