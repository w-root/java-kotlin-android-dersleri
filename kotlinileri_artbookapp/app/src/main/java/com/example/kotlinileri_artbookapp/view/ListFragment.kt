package com.example.kotlinileri_artbookapp.view

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinileri_artbookapp.R
import com.example.kotlinileri_artbookapp.adapter.ImageRowAdapter
import com.example.kotlinileri_artbookapp.adapter.RecyclerRowAdapter
import com.example.kotlinileri_artbookapp.databinding.FragmentListBinding
import com.example.kotlinileri_artbookapp.viewmodel.DetailViewModel
import com.example.kotlinileri_artbookapp.viewmodel.ListViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import javax.inject.Inject


class ListFragment @Inject constructor() :
    Fragment(R.layout.fragment_list) {

    lateinit var binding:FragmentListBinding
    lateinit var listViewModel:ListViewModel
    private val recyclerRowAdapter= RecyclerRowAdapter()

    private val swipeCallBack = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             val position = viewHolder.layoutPosition
             val selectedArt = recyclerRowAdapter.arts[position]
             listViewModel.delete(selectedArt)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(Color.WHITE)
                .addActionIcon(R.drawable.baseline_delete_24)
                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

        binding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragment2ToDetailFragment("")
            Navigation.findNavController(it).navigate(action)
        }

        binding.recyclerViewArt.adapter = recyclerRowAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        listViewModel.imageList.observe(viewLifecycleOwner, Observer {
            it?.let{
                recyclerRowAdapter.arts = it
            }
        })


    }
}