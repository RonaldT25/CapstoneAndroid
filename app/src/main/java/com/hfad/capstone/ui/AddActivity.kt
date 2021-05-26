package com.hfad.capstone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.hfad.capstone.R
import com.hfad.capstone.data.Product

class AddActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID = "extra_id"
    }
    private var extras: Int? = null
    val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activy)
        setFragment()
    }

    private fun setFragment(){
        extras = intent.getIntExtra(EXTRA_ID,0)

        val transaction = manager.beginTransaction()
        if (extras==0){
            val fragment = AddProduk()
            transaction.replace(R.id.fragment_holder,fragment)
        }
        else{
            val fragment = AddBahan()
            transaction.replace(R.id.fragment_holder,fragment)
        }

        transaction.commit()

    }
}