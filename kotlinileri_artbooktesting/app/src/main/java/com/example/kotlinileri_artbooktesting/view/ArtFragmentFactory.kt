package com.example.kotlinileri_artbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbooktesting.adapter.ImageRecyclerAdapter
import com.example.kotlinileri_artbooktesting.adapter.RecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide : RequestManager,
    val recyclerAdapter: RecyclerAdapter,
    val imageRecyclerAdapter: ImageRecyclerAdapter
) :  FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)
            ArtFragment::class.java.name -> ArtFragment(recyclerAdapter)
            ImageAPIFragment::class.java.name -> ImageAPIFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }


}