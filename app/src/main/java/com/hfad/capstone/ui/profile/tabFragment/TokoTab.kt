package com.hfad.capstone.ui.profile.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.capstone.R
import com.hfad.capstone.databinding.FragmentPenggunaTabBinding
import com.hfad.capstone.databinding.FragmentTokoTabBinding


class TokoTab : Fragment() {
    private var _binding: FragmentTokoTabBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTokoTabBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}