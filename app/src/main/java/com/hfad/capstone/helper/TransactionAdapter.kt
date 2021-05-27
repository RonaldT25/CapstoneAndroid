package com.hfad.capstone.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.capstone.R
import com.hfad.capstone.data.Transaction
import com.hfad.capstone.databinding.ItemListProdukBinding
import com.hfad.capstone.databinding.ItemListTransactionBinding
import java.util.ArrayList

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ListViewHolder>() {

    private var listData = ArrayList<Transaction>()
    var onItemClick: ((Transaction) -> Unit)? = null

    fun setData(newListData: List<Transaction>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_transaction, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListTransactionBinding.bind(itemView)
        fun bind(data: Transaction) {
            with(binding) {
                transactionProductName.text = data.product.productName
                transactionProductSold.text = data.amount.toString()
                transactionProductDate.text = data.time
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}