package com.hfad.capstone.ui.analyzesales.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.capstone.R
import com.hfad.capstone.databinding.FragmentPrimaryBinding
import com.hfad.capstone.databinding.FragmentSecondaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Secondary : Fragment() {
    private var _binding: FragmentSecondaryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecondaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextView()
    }

    private fun setTextView() {
        binding.textView.text = "Prediksi:\nBulan Depan\nTerjual: 7628\nBahan Baku yang Dibutuhkan:\n-Tempe 8269biji\n-Minyak 8269L"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}