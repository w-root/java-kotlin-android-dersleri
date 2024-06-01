package com.example.kotlinileri_artbookapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinileri_artbookapp.R
import com.example.kotlinileri_artbookapp.adapter.ImageRowAdapter
import com.example.kotlinileri_artbookapp.databinding.FragmentDetailBinding
import com.example.kotlinileri_artbookapp.databinding.FragmentSelectImageBinding
import com.example.kotlinileri_artbookapp.viewmodel.SelectImageViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectImageFragment @Inject constructor(private val imageRowAdapter: ImageRowAdapter)
    : Fragment(R.layout.fragment_select_image) {

    lateinit var binding: FragmentSelectImageBinding
    lateinit var selectImageViewModel: SelectImageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectImageViewModel = ViewModelProvider(requireActivity()).get(SelectImageViewModel::class.java)

        binding = FragmentSelectImageBinding.bind(view)

        var job: Job? = null

        binding.etSearchBar.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        selectImageViewModel.searchAndGetImagesFromAPI(it.toString())
                    }
                }
            }
        }

        observeData()
        binding.recyclerViewImages.adapter = imageRowAdapter
        binding.recyclerViewImages.layoutManager = GridLayoutManager(requireContext(),3)


    }

    fun observeData(){
        selectImageViewModel.imageList.observe(viewLifecycleOwner, Observer {
            it?.let {
                imageRowAdapter.images = it.hits.map { image ->
                    image.previewURL
                }
            }
        })
    }
}