package com.hfad.capstone.ui.produk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hfad.capstone.databinding.FragmentProdukBinding
import com.hfad.capstone.helper.SectionPagerAdapter
import com.hfad.capstone.ui.produk.tabFragment.BahanTab
import com.hfad.capstone.ui.produk.tabFragment.ProdukTab


class ProdukFragment : Fragment() {
    private var _binding: FragmentProdukBinding? = null
    private val binding get() = _binding!!
    private lateinit var pTabs: TabLayout
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: SectionPagerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProdukBinding.inflate(inflater, container, false)
        pTabs =  binding.tabs
        pViewPager = binding.myPagerView
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagerAdapters = SectionPagerAdapter(childFragmentManager)
        pagerAdapters.addFragment(ProdukTab(),"Produk")
        pagerAdapters.addFragment(BahanTab(),"Bahan")
        pViewPager.adapter = pagerAdapters
        pTabs.setupWithViewPager(pViewPager)
        pTabs.getTabAt((0))!!.setText("Produk")
        pTabs.getTabAt((1))!!.setText("Bahan")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}