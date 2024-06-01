package com.example.kotlinileri_artbookapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.RequestManager
import com.example.kotlinileri_artbookapp.R
import com.example.kotlinileri_artbookapp.databinding.FragmentDetailBinding
import com.example.kotlinileri_artbookapp.databinding.FragmentListBinding
import com.example.kotlinileri_artbookapp.viewmodel.DetailViewModel
import com.example.kotlinileri_artbookapp.viewmodel.SelectImageViewModel
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import com.example.kotlinileri_artbookapp.model.Art


class DetailFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_detail) {

    lateinit var binding: FragmentDetailBinding
    lateinit var detailViewModel : DetailViewModel
    lateinit var url:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)


        binding.imageView.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToSelectImageFragment()
            Navigation.findNavController(it).navigate(action)

        }

        arguments?.let {
            glide.load(it.getString("url")).into(binding.imageView)
            url = it.getString("url").toString()
        }

        binding.btnSave.setOnClickListener {
            val art = Art(0,binding.etName.text.toString(),
                binding.etArtisName.text.toString(),binding.etYear.text.toString(),url)
            detailViewModel.validateAndInsertArt(art)
        }
        observeLiveData()

    }


    fun observeLiveData(){
        detailViewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.equals("Enter all values")){
                    Toast.makeText(context,"Enter All Values",Toast.LENGTH_SHORT).show()
                } else if(it.equals("Error")){
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToListFragment2())
                }
            }
        })
    }

}