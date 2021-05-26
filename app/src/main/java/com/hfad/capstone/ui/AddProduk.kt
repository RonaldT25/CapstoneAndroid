package com.hfad.capstone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.capstone.R
import com.hfad.capstone.databinding.FragmentAddProdukBinding
import com.hfad.capstone.databinding.FragmentDashboardBinding
import com.hfad.capstone.helper.SessionManager


class AddProduk : Fragment() {
    private var _binding: FragmentAddProdukBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddProdukBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}