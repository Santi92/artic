package com.example.artsproject.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artsproject.databinding.ViewImageArtBinding


class ListImageRecyclerViewAdapter(
    private var values: List<String>,
) : RecyclerView.Adapter<ListImageRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ViewImageArtBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(holder.contentView.context)
            .load(values[position])
            .placeholder(ColorDrawable(Color.BLACK))
            .into(holder.imageView)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ViewImageArtBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val imageView: ImageView =  binding.artImage
        val contentView: View = binding.cardArt

    }

    fun updateProducts(list: List<String>){
        this.values = list
    }

}
