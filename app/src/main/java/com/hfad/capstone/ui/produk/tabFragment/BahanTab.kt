package com.hfad.capstone.ui.produk.tabFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Composition
import com.hfad.capstone.databinding.FragmentBahanTabBinding
import com.hfad.capstone.helper.Adapter.CompositionAdapter
import com.hfad.capstone.ui.detail.DetailComposition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BahanTab : Fragment() {
    private var _binding: FragmentBahanTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var compositionAdapter: CompositionAdapter
    private val viewModel: BahanTabViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBahanTabBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositionAdapter = CompositionAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.compositions.observe(viewLifecycleOwner, Observer {
                result -> result.let { users -> result.data?.let { getCompositions(it) } }
        })
    }


    private fun getCompositions(response : List<Composition>) {
        val  listComposition = response
        listComposition?.let {
            compositionAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailComposition::class.java)
                intent.putExtra(DetailComposition.EXTRA_COMPOSITION, selectedData)
                startActivity(intent)
            }

            compositionAdapter.setData(listComposition)

            with(binding.rvComposition) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = compositionAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}