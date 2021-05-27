package com.hfad.capstone.ui.analyzereview.tabfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.R
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.ReviewResponse
import com.hfad.capstone.databinding.FragmentNeutralBinding
import com.hfad.capstone.databinding.FragmentPositiveBinding
import com.hfad.capstone.helper.ReviewAdapter
import com.hfad.capstone.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PositiveFragment : Fragment() {
    private var _binding: FragmentPositiveBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPositiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        getReview()
    }
    private fun getReview(){
        binding.progressBar.visibility = View.VISIBLE
        val reviewAdapter = ReviewAdapter()

        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.readCrawlKomentar(sessionManager.fetchProductId()!!.toInt(),it).enqueue(object :
                Callback<ReviewResponse> {
                override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                    binding.progressBar.visibility = View.GONE
                    val  listReview = response.body()?.positive

                    reviewAdapter.setData(listReview)

                    with(binding.rvReview) {
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapter = reviewAdapter
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

                }


            })
        }
    }
}