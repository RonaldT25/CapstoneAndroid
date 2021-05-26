package com.hfad.capstone.ui.produk.tabFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.capstone.api.ClientRetrofit
import com.hfad.capstone.data.Composition
import com.hfad.capstone.databinding.FragmentBahanTabBinding
import com.hfad.capstone.helper.CompositionAdapter
import com.hfad.capstone.helper.SessionManager
import com.hfad.capstone.ui.DetailComposition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BahanTab : Fragment() {
    private var _binding: FragmentBahanTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBahanTabBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(this.requireContext())
        getCompositions()
    }

    private fun getCompositions() {
        val compositionAdapter = CompositionAdapter()

        sessionManager.fetchAuthToken()?.let {
            ClientRetrofit.instanceRetrofit.readComposition(it).enqueue(object :
                Callback<List<Composition>> {
                override fun onResponse(call: Call<List<Composition>>, response: Response<List<Composition>>) {
                    val  listComposition = response.body()!!
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

                override fun onFailure(call: Call<List<Composition>>, t: Throwable) {

                }


            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}