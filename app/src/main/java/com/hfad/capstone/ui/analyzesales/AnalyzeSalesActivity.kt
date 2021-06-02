package com.hfad.capstone.ui.analyzesales

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hfad.capstone.databinding.ActivityAnalyzeReviewBinding
import com.hfad.capstone.helper.Adapter.SectionPagerAdapter
import com.hfad.capstone.ui.analyzesales.tabFragment.Primary
import com.hfad.capstone.ui.analyzesales.tabFragment.Secondary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeSalesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalyzeReviewBinding
    private lateinit var pTabs: TabLayout
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: SectionPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeReviewBinding.inflate(layoutInflater)
        pTabs =  binding.tabs
        pViewPager = binding.myPagerView
        setViewPager()
        setContentView(binding.root)
    }
    private fun setViewPager(){
        pagerAdapters = SectionPagerAdapter(supportFragmentManager)
        pagerAdapters.addFragment(Primary(), "Prediksi Penjualan")
        pagerAdapters.addFragment(Secondary(), "Prediksi dari awal")
        pViewPager.adapter = pagerAdapters
        pTabs.setupWithViewPager(pViewPager)
        pTabs.getTabAt((0))!!.setText("Prediksi Penjualan")
        pTabs.getTabAt((1))!!.setText("Prediksi dari awal")
    }
}