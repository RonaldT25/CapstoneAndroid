package com.hfad.capstone.ui.analyzereview.tabfragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
        val reviewAdapter = ReviewAdapter()
        val  listReview = response.negativeReview
        reviewAdapter.setData(listReview)
        val base64String = response.image.negative
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        context?.let {
            Glide.with(it)
                .load(decodedImage)
                .into(binding.imageView)
        }
        with(binding.rvReview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = reviewAdapter
        }

    }
}