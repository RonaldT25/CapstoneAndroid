package com.hfad.capstone.ui


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.MainActivity
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.ResponseAuth
import com.hfad.capstone.data.User
import com.hfad.capstone.data.updateResponse
import com.hfad.capstone.databinding.ActivityDetailBinding
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.produk.ProdukFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_PRODUCT = "extra-product"
    }
    private lateinit var binding: ActivityDetailBinding
    private var extras: Product? = null
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
        supportActionBar?.title = getString(R.string.detail)
        setContentView(binding.root)
        setDetail()
    }

    private fun setDetail(){
        extras = intent.getParcelableExtra(EXTRA_PRODUCT)
        if (extras!=null){
            binding.detailTitle.text = extras!!.productName
            binding.editTextHarga.setHint(extras!!.price.toString())
            binding.editTextTautan.setHint(extras!!.tokopediaProductUrl.toString())
        }
        binding.btnSave.setOnClickListener{
            update()
        }
    }
    private fun update(){
        if (binding.editTextHarga.text.isEmpty()) {
            binding.editTextHarga.error = getText(R.string.usernamewarning)
            binding.editTextHarga.requestFocus()
        }
        else{
            sessionManager.fetchAuthToken()?.let {
                extras?.let { it1 ->
                    ClientRetrofit.instanceRetrofit.updateProduct(
                        it1.id,
                        it1.productName,
                        binding.editTextHarga.text.toString().toInt(),
                        it
                    ).enqueue(object : Callback<updateResponse> {
                        override fun onResponse(call: Call<updateResponse>, response: Response<updateResponse>) {
                            Toast.makeText(this@DetailActivity, response.body()?.message,LENGTH_SHORT).show()
                            val intent = Intent(this@DetailActivity, MainActivity::class.java)
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