package com.example.artsproject.ui.list

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artsproject.R
import com.example.artsproject.databinding.FragmentListArtBinding
import com.example.artsproject.ui.entity.toEntityUI
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListArtFragment : Fragment() {

     private  var binding: FragmentListArtBinding? = null
     private val artsViewModel: ArtsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (binding == null){
            binding=  FragmentListArtBinding.inflate(inflater, container, false)
            sharedElementReturnTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }




        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if ( binding!!.list.adapter == null){
            initView()
            initialiseViewModel()
        }
    }

    private fun initView(){
        val fragment = this
        with(binding!!.list) {

            layoutManager = GridLayoutManager(context, 2)

            adapter = ListArtRecyclerViewAdapter(
                listOf(),
            ){ art , image ->

                val extras = FragmentNavigatorExtras(
                    image to art.id.toString()
                )
                NavHostFragment
                    .findNavController(fragment =  fragment)

                    .navigate(
                        ListArtFragmentDirections
                            .actionListArtFragmentToDetailArtFragment(art = art.toEntityUI()),
                        extras,
                    )

            }

        }
    }

    fun  initialiseViewModel(){
        artsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            binding!!.progressCircular.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE

            binding!!.contentError.visibility  = if (uiState.isError) View.VISIBLE else View.GONE

            if(uiState.arts.isNotEmpty() && !uiState.isError ){
                val adapter = binding!!.list.adapter

                if(adapter is ListArtRecyclerViewAdapter){
                    adapter.updateProducts(uiState.arts)
                    adapter.notifyDataSetChanged()

                }
                binding!!.list.visibility = View.VISIBLE
            }
        }
        setupListener()
    }

    private fun setupListener() {
        binding!!.contentError.setOnClickListener {
            artsViewModel.onLoad()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artsViewModel.onLoad()
    }

}