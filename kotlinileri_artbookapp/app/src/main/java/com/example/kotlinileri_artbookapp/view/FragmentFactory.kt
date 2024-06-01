package com.example.kotlinileri_artbookapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.ListFragment
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbookapp.adapter.ImageRowAdapter
import com.example.kotlinileri_artbookapp.adapter.RecyclerRowAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val glide : RequestManager,
    private val imageRowAdapter: ImageRowAdapter,
    private val recyclerRowAdapter: RecyclerRowAdapter
) :  FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            SelectImageFragment::class.java.name -> SelectImageFragment(imageRowAdapter)
            DetailFragment::class.java.name -> DetailFragment(glide)

            else -> super.instantiate(classLoader, className)
        }

    }


}