package com.example.artsproject.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionInflater

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.artsproject.databinding.FragmentDetialArtBinding
import com.example.artsproject.dateFormatShort
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailArtFragment : Fragment() {

    private lateinit var binding: FragmentDetialArtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetialArtBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            val params = DetailArtFragmentArgs.fromBundle(this)
            val art = params.art

            binding.image.transitionName = art.id.toString()

            binding.txtDescription.text = art.provenance
            binding.txTitle.text = art.artist
            binding.txReleaseDate.text = art.timestamp!!.dateFormatShort()

            Glide
                .with(this@DetailArtFragment)
                .load(art.imgUrl)
                .placeholder(ColorDrawable(Color.BLACK))
                .into(binding.image)


            with(binding.listImage) {

                layoutManager = GridLayoutManager(context, 2)

                adapter = ListImageRecyclerViewAdapter(
                    art.listImage,
                )

            }

        }

    }


}