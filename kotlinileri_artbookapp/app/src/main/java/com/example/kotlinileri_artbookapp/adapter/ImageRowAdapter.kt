package com.example.kotlinileri_artbookapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbookapp.R
import com.example.kotlinileri_artbookapp.view.SelectImageFragmentDirections
import javax.inject.Inject

class ImageRowAdapter @Inject constructor(
    private val glide : RequestManager
): RecyclerView.Adapter<ImageRowAdapter.VHolder>() {

    class VHolder(itemView:View) : RecyclerView.ViewHolder(itemView){}

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_recycler_row,parent,false)
        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val imageView2 = holder.itemView.findViewById<ImageView>(R.id.imageView2)

        glide.load(images[position]).into(imageView2)

        imageView2.setOnClickListener {
            val action = SelectImageFragmentDirections.actionSelectImageFragmentToDetailFragment(images[position])
            Navigation.findNavController(it).navigate(action)

        }
    }



}