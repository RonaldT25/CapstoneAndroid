package com.hfad.capstone.helper.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.capstone.R
import com.hfad.capstone.databinding.ItemListReviewBinding
import java.util.ArrayList

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ListViewHolder>() {

    private var listData = ArrayList<String>()
    var onItemClick: ((String) -> Unit)? = null

    fun setData(newListData: List<String>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_review, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListReviewBinding.bind(itemView)
        fun bind(data: String) {
            with(binding) {
                tvReview.text = data
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}