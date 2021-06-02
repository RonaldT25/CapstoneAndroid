package com.hfad.capstone.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.Transaction
import com.hfad.capstone.databinding.ActivityDetailTransactionBinding
import com.hfad.capstone.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTransaction : AppCompatActivity() {

    companion object{
        const val EXTRA_TRANSACTION = "extra-transaction"
    }
    private lateinit var binding: ActivityDetailTransactionBinding
    private var extras: Transaction? = null
    private lateinit var clientRetrofit: ClientRetrofit
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        clientRetrofit = ClientRetrofit()
        sessionManager = SessionManager(this)
        supportActionBar?.title = getString(R.string.detail_transaksi)
        setContentView(binding.root)
        setDetail()
    }

    private fun setDetail() {
        extras = intent.getParcelableExtra(EXTRA_TRANSACTION)
        if (extras!=null){
            binding.detailProductTitle.text = extras!!.product.productName
            binding.Harga.text = extras!!.product.price.toString()
            binding.editTerjual.setHint(extras!!.amount.toString())
        }
        binding.deleteComposition.setOnClickListener{
            extras?.let { it1 -> delete(it1) }
        }
        binding.btnSave.setOnClickListener {
            extras?.let { it1 -> update(it1) }
        }
    }

    private fun update(transaction: Transaction) {
        GlobalScope.launch(Dispatchers.Main) {
            val response =
                clientRetrofit.getApiService(this@DetailTransaction).updateTransactions(
                    transaction.productId,transaction.id,binding.editTerjual.text.toString().toInt())
            if (response.isSuccessful) {
                Toast.makeText(this@DetailTransaction, response.body()?.message, Toast.LENGTH_SHORT).show()
                this@DetailTransaction.finish()
            }


        }
    }

    private fun delete(transaction : Transaction) {
        GlobalScope.launch(Dispatchers.Main) {
                val response =
                    clientRetrofit.getApiService(this@DetailTransaction).deleteTransactions(
                        transaction.productId,transaction.id)
                if (response.isSuccessful) {
                    Toast.makeText(this@DetailTransaction, response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    this@DetailTransaction.finish()
                }


        }
    }
}