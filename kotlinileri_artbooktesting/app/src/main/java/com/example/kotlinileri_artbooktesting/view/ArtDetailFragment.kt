package com.example.kotlinileri_artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbooktesting.R
import com.example.kotlinileri_artbooktesting.databinding.FragmentArtDetailsBinding
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(val glide : RequestManager) : Fragment(R.layout.fragment_art_details) {

    private lateinit var binding : FragmentArtDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArtDetailsBinding.bind(view)

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToImageAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}