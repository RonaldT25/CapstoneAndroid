package com.hfad.capstone.ui.analyzereview.tabfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.databinding.FragmentNegativeBinding
import com.hfad.capstone.helper.Adapter.ReviewAdapter
import com.hfad.capstone.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NegativeFragment : Fragment() {
    private var _binding: FragmentNegativeBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientRetrofit: ClientRetrofit
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNegativeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientRetrofit = ClientRetrofit()
        sessionManager = SessionManager(this.requireContext())
        getReview()
    }

    private fun getReview(){
        binding.progressBar.visibility = View.VISIBLE
        val reviewAdapter = ReviewAdapter()

        GlobalScope.launch(Dispatchers.Main) {
            val response = context?.let { clientRetrofit.getApiService(it).readCrawlKomentar(sessionManager.fetchProductId()!!.toInt()) }
            if (response != null) {
                if (response.isSuccessful){
                    binding.progressBar.visibility = View.GONE
                    val  listReview = response.body()?.negative

                    reviewAdapter.setData(listReview)

                    with(binding.rvReview) {
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapter = reviewAdapter
                    }
                }
            }
        }
    }
}