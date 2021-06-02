package com.hfad.capstone.ui.add

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
                                val response = clientRetrofit.getApiService(requireContext()).insertCompositionDetail(
                                        it1.toInt(),
                                        list,
                                        it1.toInt(),
                                        binding.quantityNumber.text.toString().toFloat()
                                )
                                if (response.isSuccessful) {
                                    setupObservers()
                                }
                            }
                        }
                    }
                }
            })
        }
        binding.btnAddDetailBahan.setOnClickListener {
            activity?.finish()
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
        binding.ACTextView.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                setUnit()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setUnit()
            }
        })


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


    private fun setUnit() {
        viewModel.searchUnit(binding.ACTextView.text.toString()).observe(viewLifecycleOwner,{
            list -> binding.satuanBahan.text = list
        })
    }

    private fun getCompositionDetail(response: List<CompositionDetailEntity>){
        val detailCompositionAdapter = DetailCompositionAdapter()
        val  listReview = response
        detailCompositionAdapter.onItemClick = { selectedData ->
            delete(selectedData)
        }
        detailCompositionAdapter.setData(listReview)
        with(binding.rvDetailBahanBaku) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = detailCompositionAdapter
        }

    }

    private fun delete(compositionDetail:CompositionDetailEntity) {
        viewModel.search(compositionDetail.composition.compositionName).observe(viewLifecycleOwner, { list ->
            list.let {
                if (list != 0) {
                    GlobalScope.launch(Dispatchers.Main) {
                        sessionManager.fetchProductId()?.let { it1 ->
                           val response =  clientRetrofit.getApiService(requireContext()).deleteCompositionDetail(
                                it1.toInt(),
                                list
                            )
                            if (response.isSuccessful) {
                                setupObservers()
                            }
                        }

                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}