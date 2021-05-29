package com.hfad.capstone.ui.analyzereview.tabfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.database.Resource
import com.hfad.capstone.data.database.ReviewResponseEntity
import com.hfad.capstone.databinding.FragmentNegativeBinding
import com.hfad.capstone.helper.Adapter.ReviewAdapter
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NegativeFragment : Fragment() {
    private var _binding: FragmentNegativeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NegativeFragmentViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNegativeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        setupObservers()
    }

    private fun setupObservers() {
        sessionManager.fetchProductId()?.let {
            viewModel.getReview(it.toInt()).observe(viewLifecycleOwner, Observer {
                    result -> result.let { users -> result.data?.let { getReview(it) } }
                binding.progressBar.isVisible = result is Resource.Loading
            })
        }
    }

    private fun getReview(response : ReviewResponseEntity){
        binding.progressBar.visibility = View.VISIBLE
        val reviewAdapter = ReviewAdapter()
        binding.progressBar.visibility = View.GONE
        val  listReview = response.negativeReview
        reviewAdapter.setData(listReview)
        with(binding.rvReview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = reviewAdapter
        }

    }
}