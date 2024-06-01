package com.example.kotlinileri_countriesapp.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinileri_countriesapp.adapter.CountryAdapter
import com.example.kotlinileri_countriesapp.databinding.FragmentFeedBinding
import com.example.kotlinileri_countriesapp.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var feedViewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        feedViewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        feedViewModel.refreshData()


        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            binding.errorTextView.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            feedViewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeLiveData(){
        feedViewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerView.visibility = View.VISIBLE
                countryAdapter.updateList(it)
            }

        })

        feedViewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
               if(it){
                   binding.errorTextView.visibility = View.VISIBLE
                   binding.countryLoading.visibility = View.GONE
               } else {
                   binding.errorTextView.visibility = View.GONE
               }
            }

        })

        feedViewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.countryLoading.visibility = View.GONE
                }
            }

        })
    }
}