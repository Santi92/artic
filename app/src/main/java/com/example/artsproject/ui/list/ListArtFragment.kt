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
import androidx.recyclerview.widget.RecyclerView





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
            val layoutManagerGrid =  GridLayoutManager(context, 2)
            layoutManager = layoutManagerGrid

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

            var loading = true
            var pastVisiblesItems: Int
            var visibleItemCount: Int
            var totalItemCount: Int

            binding!!.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        Log.d("onScrolled", "Enter")//check for scroll down
                        visibleItemCount = layoutManagerGrid.getChildCount()
                        totalItemCount = layoutManagerGrid.getItemCount()
                        pastVisiblesItems = layoutManagerGrid.findFirstVisibleItemPosition()
                        android.util.Log.d("onScrolled", "load" + artsViewModel.uiState.value?.isLoadingMoreArt)
                        if (artsViewModel.uiState.value?.isLoadingMoreArt == false) {
                            Log.d("onScrolled", "load")

                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                artsViewModel.onLoadNext()
                            }
                        }

                    }
                }
            })
        }
    }

    private fun  initialiseViewModel(){
        artsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            binding!!.progressCircular.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE

            binding!!.contentError.visibility  = if (uiState.isError) View.VISIBLE else View.GONE

            binding!!.progressCircularMore.visibility  = if (uiState.isLoadingMoreArt) View.VISIBLE else View.GONE

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