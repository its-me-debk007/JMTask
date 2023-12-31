package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentSearchBinding
import com.example.jmtask.ui.adapter.FavouriteMoviesRecyclerAdapter
import com.example.jmtask.util.favouritesList
import com.google.android.material.transition.MaterialSharedAxis

class FavouritesFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        binding.textInputLayout.isVisible = false
        binding.searchBtn.isVisible = false
        binding.heading.text = "Favourites"
        binding.movieRecyclerView.isVisible = true

        val adapter = FavouriteMoviesRecyclerAdapter(
            favouritesList,
            requireContext()
        ) { movie ->
            val action =
                FavouritesFragmentDirections.actionFavouritesFragmentToDetailFragment(movie)
            findNavController().navigate(action)
        }

        binding.movieRecyclerView.adapter = adapter

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                favouritesList.removeAt(position)
                adapter.removeData(favouritesList, position)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.movieRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}