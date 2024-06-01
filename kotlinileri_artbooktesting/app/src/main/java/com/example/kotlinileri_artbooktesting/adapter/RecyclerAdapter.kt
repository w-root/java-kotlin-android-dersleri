package com.example.kotlinileri_artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbooktesting.R
import com.example.kotlinileri_artbooktesting.model.Art
import javax.inject.Inject

class RecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<RecyclerAdapter.VHolder>() {

    class VHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        val tvName = holder.itemView.findViewById<TextView>(R.id.tvName)
        val tvArtYear = holder.itemView.findViewById<TextView>(R.id.tvArtYear)
        val tvArtisName = holder.itemView.findViewById<TextView>(R.id.tvArtisName)

        val art = arts[position]

        holder.itemView.apply {
            tvName.text = "Name : ${art.name}"
            tvArtYear.text = "Name : ${art.year}"
            tvArtisName.text = "Name : ${art.artisName}"
            glide.load(art.imageUrl).into(imageView)
        }
    }



}