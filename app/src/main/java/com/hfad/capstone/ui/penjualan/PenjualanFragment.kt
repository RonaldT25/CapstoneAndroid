package com.hfad.capstone.ui.penjualan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hfad.capstone.R
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.databinding.FragmentPenjualanBinding
import com.hfad.capstone.databinding.FragmentProdukBinding

class PenjualanFragment : Fragment() {
    private var _binding: FragmentPenjualanBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPenjualanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}