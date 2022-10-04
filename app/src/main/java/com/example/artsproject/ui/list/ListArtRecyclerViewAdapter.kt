package com.example.artsproject.ui.list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artsproject.data.dto.Art
import com.example.artsproject.databinding.ViewItemArtBinding


class ListArtRecyclerViewAdapter(
    private var values: List<Art>,
    private var onItemClicked: ((product: Art) -> Unit)
) : RecyclerView.Adapter<ListArtRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ViewItemArtBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = values[position].apply {
            holder.idView.text = id.toString()
            holder.description.text = medium_display ?: ""


            Glide
                .with(holder.contentView.context)
                .load(getUrlImage())
                .placeholder(ColorDrawable(Color.BLACK))
                .into(holder.imageView)
        }

        holder.contentView.setOnClickListener { onItemClicked.invoke(product) }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ViewItemArtBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val description: TextView = binding.description
        val imageView: ImageView =  binding.itemImage
        val contentView: View = binding.itemContent

        override fun toString(): String {
            return super.toString() + " '" + description.text + "'"
        }
    }

    fun updateProducts(list: List<Art>){
        this.values = list
    }

}
