package com.example.kotlinileri_artbookapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbookapp.R
import com.example.kotlinileri_artbookapp.model.Art
import javax.inject.Inject


class RecyclerRowAdapter @Inject constructor(
    //private val glide : RequestManager
): RecyclerView.Adapter<RecyclerRowAdapter.RecyclerVHolder>() {

    class RecyclerVHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return RecyclerVHolder(view)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: RecyclerVHolder, position: Int) {
        val imageView3 = holder.itemView.findViewById<ImageView>(R.id.imageView3)
        val name = holder.itemView.findViewById<TextView>(R.id.tvName)
        val artisName = holder.itemView.findViewById<TextView>(R.id.tvArtisName)
        val year = holder.itemView.findViewById<TextView>(R.id.tvYear)

    //    glide.load(arts[position].imageUrl).into(imageView3)

        name.setText(arts[position].name)
        artisName.setText(arts[position].artisName)
        year.setText(arts[position].year)


    }



}