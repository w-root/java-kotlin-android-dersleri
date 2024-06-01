package com.example.kotlinileri_artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinileri_artbooktesting.R
import com.example.kotlinileri_artbooktesting.adapter.RecyclerAdapter
import com.example.kotlinileri_artbooktesting.databinding.FragmentsArtsBinding
import com.example.kotlinileri_artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(val recyclerAdapter: RecyclerAdapter) : Fragment(R.layout.fragments_arts) {

    private lateinit var binding : FragmentsArtsBinding
    lateinit var viewModel : ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val selectedArt = recyclerAdapter.arts[position]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding = FragmentsArtsBinding.bind(view)

        subscriveToObservers()
        binding.recyclerViewArt.adapter = recyclerAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailFragment())
        }


    }

    private fun subscriveToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.arts = it
        })
    }


}