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

class ImageRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ImageRecyclerAdapter.VHolder>() {

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var onItemClickListener : ((String) -> Unit)? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecyclerAdapter.VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return VHolder(view)
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }
    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageRecyclerAdapter.VHolder, position: Int) {
        val imageView2 = holder.itemView.findViewById<ImageView>(R.id.imageView2)
        val url = images[position]

        holder.itemView.apply {
            glide.load(url).into(imageView2)
            setOnItemClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }


}