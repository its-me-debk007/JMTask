package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentSearchBinding
import com.example.jmtask.ui.adapter.FavouriteMoviesRecyclerAdapter
import com.example.jmtask.ui.viewmodel.JMViewModel
import com.example.jmtask.util.ApiState
import com.example.jmtask.util.hideKeyboard
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private var moviesRecyclerAdapter: FavouriteMoviesRecyclerAdapter? = null
    var query: String = ""
    private val viewModel: JMViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

//        lifecycleScope.launch(Dispatchers.IO) { getResp("harry potter") }

        binding.searchBtn.setOnClickListener { executeSearch() }

        binding.textInputEditText.setOnEditorActionListener { _, _, _ ->
            executeSearch()
            true
        }
    }

    private fun executeSearch() {
        query = binding.textInputEditText.text?.trim().toString()
        lifecycleScope.launch(Dispatchers.IO) { getResp(query) }
        binding.textInputEditText.text?.clear()
        hideKeyboard(binding.root, activity)
    }

    private suspend fun getResp(query: String) {
        viewModel.searchMovies(query)
            .flowWithLifecycle(lifecycle)
            .collectLatest {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is ApiState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.errorTextView.isVisible = false
                            binding.movieRecyclerView.isVisible = false
                        }

                        is ApiState.Error -> {
                            Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                            Log.d("RETRO", it.errorMsg.toString())
                        }

                        is ApiState.Success -> {
                            binding.progressBar.hide()

                            if (it.data!!.isEmpty()) {
                                binding.errorTextView.isVisible = true
                                binding.movieRecyclerView.isVisible = false
                                binding.errorTextView.text = "No movie found :("
                                return@withContext
                            }

                            moviesRecyclerAdapter?.updateData(it.data) ?: run {

                                moviesRecyclerAdapter = FavouriteMoviesRecyclerAdapter(
                                    it.data,
                                    requireContext()
                                ) { movie ->
                                    val action =
                                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                                            movie
                                        )
                                    findNavController().navigate(action)
                                }

                                binding.movieRecyclerView.adapter = moviesRecyclerAdapter
                            }

                            binding.errorTextView.isVisible = false
                            binding.movieRecyclerView.isVisible = true
                        }
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}