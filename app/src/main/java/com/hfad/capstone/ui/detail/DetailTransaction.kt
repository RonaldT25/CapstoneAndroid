package com.hfad.capstone.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.capstone.R
import com.hfad.capstone.data.Product
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.databinding.ActivityDetailBinding
import com.hfad.capstone.databinding.ActivityDetailTransactionBinding

class DetailTransaction : AppCompatActivity() {

    companion object{
        const val EXTRA_TRANSACTION = "extra-transaction"
    }
    private lateinit var binding: ActivityDetailTransactionBinding
    private var extras: Transaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        supportActionBar?.title = getString(R.string.detail)
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
    }
}