package com.hfad.capstone.helper.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.capstone.R
import com.hfad.capstone.data.Product
import com.hfad.capstone.databinding.ItemListProdukBinding
import java.util.ArrayList

class ProdukAdapter : RecyclerView.Adapter<ProdukAdapter.ListViewHolder>() {

    private var listData = ArrayList<Product>()
    var onItemClick: ((Product) -> Unit)? = null

    fun setData(newListData: List<Product>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_produk, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListProdukBinding.bind(itemView)
        fun bind(data: Product) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(data.image)
                        .into(ivItemImage)
                tvItemTitle.text = data.productName
                tvItemPrice.text = data.price.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}