package com.hfad.capstone.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.capstone.R

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
        else if (extras==1){
            val fragment = AddBahan()
            transaction.replace(R.id.fragment_holder,fragment)
        }
        else if (extras==2){
            val fragment = AddTransaction()
            transaction.replace(R.id.fragment_holder,fragment)
        }
        else if (extras==3){
            val fragment = AddDetailBahanBaku()
            transaction.replace(R.id.fragment_holder,fragment)
        }

        transaction.commit()

    }
}