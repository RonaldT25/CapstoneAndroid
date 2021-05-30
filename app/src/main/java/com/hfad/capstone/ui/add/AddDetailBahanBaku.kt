package com.hfad.capstone.ui.add

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.model.Composition
import com.hfad.capstone.data.database.CompositionDetailEntity
import com.hfad.capstone.databinding.FragmentAddDetailBahanBakuBinding
import com.hfad.capstone.helper.Adapter.DetailCompositionAdapter
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddDetailBahanBaku : Fragment() {
    private var _binding: FragmentAddDetailBahanBakuBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<String>()
    private val viewModel: AddDetailBahanBakuViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private lateinit var clientRetrofit: ClientRetrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddDetailBahanBakuBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        clientRetrofit = ClientRetrofit()
        setupObservers()
        setButtons()
    }

    private fun setButtons() {
        binding.btnAddBahan.setOnClickListener {
            viewModel.search(binding.ACTextView.text.toString()).observe(viewLifecycleOwner, { list ->
                list.let {
                    if (list != 0) {
                        GlobalScope.launch(Dispatchers.Main) {
                            sessionManager.fetchProductId()?.let { it1 ->
                                clientRetrofit.getApiService(requireContext()).insertCompositionDetail(
                                        it1.toInt(),
                                        list,
                                        it1.toInt(),
                                        binding.quantityNumber.text.toString().toFloat()
                                )
                            }
                        }
                        setupObservers()
                    }
                }
            })
        }
        binding.btnAddDetailBahan.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        sessionManager.fetchProductId()?.let {
            viewModel.getCompositionDetail(it.toInt()).observe(viewLifecycleOwner, Observer { result ->
                result.let { users -> result.data?.let { getCompositionDetail(it) } }
            })
        }
        viewModel.compositions.observe(viewLifecycleOwner, Observer { result ->
            result.let { users -> result.data?.let { getCompositions(it) } }
        })
        setSpinner()
    }

    private fun setSpinner() {
           val arrayAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, list)
           arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.ACTextView.setAdapter(arrayAdapter)
    }

    private fun getCompositions(composition: List<Composition>) {
        list.clear()
        for(name in composition){
            list.add(name.compositionName)
        }
    }

    private fun getCompositionDetail(response: List<CompositionDetailEntity>){
        val detailCompositionAdapter = DetailCompositionAdapter()
        val  listReview = response
        detailCompositionAdapter.setData(listReview)
        with(binding.rvDetailBahanBaku) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = detailCompositionAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}