package com.example.kotlinileri_artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.kotlinileri_artbooktesting.R
import com.example.kotlinileri_artbooktesting.adapter.ImageRecyclerAdapter
import com.example.kotlinileri_artbooktesting.databinding.FragmentImageApiBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(val imageRecyclerAdapter: ImageRecyclerAdapter) : Fragment(R.layout.fragment_image_api) {

    private lateinit var binding : FragmentImageApiBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job:Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        //viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        binding = FragmentImageApiBinding.bind(view)


    }
}