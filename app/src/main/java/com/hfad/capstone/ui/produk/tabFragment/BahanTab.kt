package com.hfad.capstone.ui.produk.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.capstone.R
import com.hfad.capstone.databinding.FragmentBahanTabBinding
import com.hfad.capstone.databinding.FragmentProdukBinding


class BahanTab : Fragment() {
    private var _binding: FragmentBahanTabBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBahanTabBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}