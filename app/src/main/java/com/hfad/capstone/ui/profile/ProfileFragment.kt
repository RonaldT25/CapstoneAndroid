package com.hfad.capstone.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hfad.capstone.R
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.databinding.FragmentPenjualanBinding
import com.hfad.capstone.databinding.FragmentProfileBinding
import com.hfad.capstone.helper.SectionPagerAdapter
import com.hfad.capstone.ui.produk.tabFragment.BahanTab
import com.hfad.capstone.ui.produk.tabFragment.ProdukTab
import com.hfad.capstone.ui.profile.tabFragment.PenggunaTab
import com.hfad.capstone.ui.profile.tabFragment.TokoTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var pTabs: TabLayout
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: SectionPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        pTabs =  binding.tabs
        pViewPager = binding.myPagerView
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagerAdapters = SectionPagerAdapter(childFragmentManager)
        pagerAdapters.addFragment(PenggunaTab(),"Pengguna")
        pagerAdapters.addFragment(TokoTab(),"Toko")
        pViewPager.adapter = pagerAdapters
        pTabs.setupWithViewPager(pViewPager)
        pTabs.getTabAt((0))!!.setText("Pengguna")
        pTabs.getTabAt((1))!!.setText("Toko")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
