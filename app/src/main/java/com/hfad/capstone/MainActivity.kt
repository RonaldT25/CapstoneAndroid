package com.hfad.capstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.capstone.ui.dashboard.dashboard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, dashboard())
            .commit()

    }
}