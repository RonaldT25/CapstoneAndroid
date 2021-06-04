package com.hfad.capstone.helper.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.capstone.R
import com.hfad.capstone.data.model.Product
import com.hfad.capstone.data.model.Sales
import com.hfad.capstone.databinding.ItemListProdukBinding
import com.hfad.capstone.databinding.ItemListSalesBinding
import java.util.ArrayList

class SalesAdapter : RecyclerView.Adapter<SalesAdapter.ListViewHolder>() {

    private var listData = ArrayList<Sales>()
    var onItemClick: ((Sales) -> Unit)? = null

    fun setData(newListData: List<Sales>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_sales, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListSalesBinding.bind(itemView)
        fun bind(data: Sales) {
            with(binding) {
                date.text = data.date
                monthly.text = data.result.toString().replace("{","").replace("}","").replace(",","\n")
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}