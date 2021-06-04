package com.hfad.capstone.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hfad.capstone.databinding.FragmentProfileBinding
import com.hfad.capstone.databinding.FragmentProfileUserBinding
import com.hfad.capstone.helper.Adapter.SectionPagerAdapter
import com.hfad.capstone.ui.profile.tabFragment.PenggunaTab
import com.hfad.capstone.ui.profile.tabFragment.TokoTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUserFragment : Fragment() {
    private var _binding: FragmentProfileUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var pTabs: TabLayout
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: SectionPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileUserBinding.inflate(inflater, container, false)
        pTabs =  binding.tabs
        pViewPager = binding.myPagerView
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagerAdapters = SectionPagerAdapter(childFragmentManager)
        pagerAdapters.addFragment(PenggunaTab(),"Pengguna")
        pViewPager.adapter = pagerAdapters
        pTabs.setupWithViewPager(pViewPager)
        pTabs.getTabAt((0))!!.setText("Pengguna")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
