package com.hfad.capstone.ui.analyzereview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hfad.capstone.R
import com.hfad.capstone.databinding.ActivityAnalyzeReviewBinding
import com.hfad.capstone.databinding.ActivityDetailBinding
import com.hfad.capstone.helper.SectionPagerAdapter
import com.hfad.capstone.ui.analyzereview.tabfragment.NegativeFragment
import com.hfad.capstone.ui.analyzereview.tabfragment.NeutralFragment
import com.hfad.capstone.ui.analyzereview.tabfragment.PositiveFragment
import com.hfad.capstone.ui.produk.tabFragment.BahanTab
import com.hfad.capstone.ui.produk.tabFragment.ProdukTab

class AnalyzeReviewActivity : AppCompatActivity() {
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
        pagerAdapters.addFragment(NegativeFragment(), "Negative")
        pagerAdapters.addFragment(NeutralFragment(), "Neutral")
        pagerAdapters.addFragment(PositiveFragment(), "Positive")
        pViewPager.adapter = pagerAdapters
        pTabs.setupWithViewPager(pViewPager)
        pTabs.getTabAt((0))!!.setText("Negative")
        pTabs.getTabAt((1))!!.setText("Neutral")
        pTabs.getTabAt((2))!!.setText("Positive")
    }
}