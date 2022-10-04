package com.example.artsproject.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.artsproject.R
import com.example.artsproject.databinding.FragmentListArtBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListArtFragment : Fragment() {

     private lateinit var binding: FragmentListArtBinding
     private val artsViewModel: ArtsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=  FragmentListArtBinding.inflate(inflater, container, false)

        with(binding.list) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ListArtRecyclerViewAdapter(
                listOf(),
            ){ product ->
                /*val action = ProductsFragmentDirections.actionDetailProduct(
                    product.title,
                    product.description,
                    product.imageUrl,
                )

                this@ProductsFragment.findNavController().navigate(action)*/
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artsViewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            binding.progressCircular.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE

            binding.contentError.visibility  = if (uiState.isError) View.VISIBLE else View.GONE

            if(uiState.arts.isNotEmpty() && !uiState.isError ){
                val adapter = binding.list.adapter

                if(adapter is ListArtRecyclerViewAdapter){
                    adapter.updateProducts(uiState.arts)
                    adapter.notifyDataSetChanged()
                }
                binding.list.visibility = View.VISIBLE
            }
        }
        setupListener()
    }

    private fun setupListener() {
        binding.contentError.setOnClickListener {
            artsViewModel.onLoad()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artsViewModel.onLoad()
    }

}