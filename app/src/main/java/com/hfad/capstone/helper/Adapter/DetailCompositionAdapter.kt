package com.hfad.capstone.helper.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.capstone.R
import com.hfad.capstone.data.database.CompositionDetailEntity
import com.hfad.capstone.databinding.ItemListCompositionBinding
import com.hfad.capstone.databinding.ItemListDetailCompositionBinding
import java.util.*

class DetailCompositionAdapter : RecyclerView.Adapter<DetailCompositionAdapter.ListViewHolder>() {

    private var listData = ArrayList<CompositionDetailEntity>()
    var onItemClick: ((CompositionDetailEntity) -> Unit)? = null

    fun setData(newListData: List<CompositionDetailEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_detail_composition, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListDetailCompositionBinding.bind(itemView)
        fun bind(data: CompositionDetailEntity) {
            with(binding) {
                tvCompositionTitle.text = data.composition.compositionName
                tvCompositionUnit.text = data.amount.toString()
            }
        }

        init {
            binding.button.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}