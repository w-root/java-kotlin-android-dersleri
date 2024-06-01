package com.example.kotlinileri_countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinileri_countriesapp.R
import com.example.kotlinileri_countriesapp.databinding.RecyclerRowBinding
import com.example.kotlinileri_countriesapp.model.Country
import com.example.kotlinileri_countriesapp.util.downloadFromUrl
import com.example.kotlinileri_countriesapp.util.placeHolderProgessBar
import com.example.kotlinileri_countriesapp.view.FeedFragmentDirections

class CountryAdapter(private val countryList : ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.VHolder>() ,CountryClickListener{
    private lateinit var recyclerRowBinding: RecyclerRowBinding

    class VHolder(val binding:RecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        //val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         recyclerRowBinding = DataBindingUtil.inflate<RecyclerRowBinding>(LayoutInflater.from(parent.context),
            R.layout.recycler_row,parent,false)
        return VHolder(recyclerRowBinding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.binding.country = countryList[position]
        holder.binding.listener = this

    //        holder.binding.tvCountryName.text = countryList[position].countryName
    //        holder.binding.tvCountryRegion.text = countryList[position].countryRegion
    //        holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl,
    //            placeHolderProgessBar(holder.itemView.context))
    //
    //        holder.itemView.setOnClickListener {
    //            val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(countryList[position].uuid)
    //            Navigation.findNavController(it).navigate(action)
    //        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid = recyclerRowBinding.countryUUID.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(uuid)
        Navigation.findNavController(v).navigate(action)

    }
}