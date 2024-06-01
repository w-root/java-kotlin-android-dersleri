package com.example.kotlinileri_countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.kotlinileri_countriesapp.databinding.FragmentDetailBinding
import com.example.kotlinileri_countriesapp.databinding.FragmentFeedBinding
import com.example.kotlinileri_countriesapp.util.downloadFromUrl
import com.example.kotlinileri_countriesapp.util.placeHolderProgessBar
import com.example.kotlinileri_countriesapp.viewmodel.CountryViewModel
import com.example.kotlinileri_countriesapp.viewmodel.FeedViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var countryViewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        arguments?.let {
            countryViewModel.getDataFromRoom(it.getInt("id"))
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        countryViewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.tvCountryName2.text = it.countryName
                binding.tvCountryCapital.text = it.countryCapital
                binding.tvCountryCurrency.text = it.countryCurrency
                binding.tvCountryLanguage.text = it.countryLanguage
                binding.tvCountryRegion.text = it.countryRegion
                binding.imageView2.downloadFromUrl(it.imageUrl, placeHolderProgessBar(context!!))
            }
        })
    }
}