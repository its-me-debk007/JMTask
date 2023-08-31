package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentHomeBinding
import com.example.jmtask.ui.adapter.MovieRecyclerAdapter
import com.example.jmtask.ui.viewmodel.JMViewModel
import com.example.jmtask.util.ApiState
import com.example.jmtask.util.ConnectivityStateManager
import com.example.jmtask.util.NO_INTERNET
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val viewModel: JMViewModel by viewModels()
    private val snackbar: Snackbar by lazy {
        Snackbar.make(binding.root, NO_INTERNET, Snackbar.LENGTH_INDEFINITE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        lifecycleScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                ConnectivityStateManager.observeNetworkState(requireContext())
                    .collect { isInternetAvailable ->
                        if (isInternetAvailable) snackbar.dismiss() else showSnackbar()
                    }
            }

            getResp()
        }

    }

    private suspend fun getResp() {
        viewModel.getMovies()
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
//                            Log.d("RETRO", it.errorMsg.toString())
                        }

                        is ApiState.Success -> {
                            binding.progressBar.isVisible = false
                            binding.errorTextView.isVisible = false
                            binding.movieRecyclerView.isVisible = true
                            binding.movieRecyclerView.adapter =
                                MovieRecyclerAdapter(
                                    it.data!!,
                                    requireContext()
                                ) { movie ->
                                    val action =
                                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                            movie
                                        )
                                    findNavController().navigate(action)
                                }
                        }
                    }
                }
            }
    }

    private fun showSnackbar() {
        snackbar.apply {
//            setAction(R.string.retry) {
//                dismiss()
//                lifecycleScope.launch(Dispatchers.IO) { getResp() }
//            }
            animationMode = Snackbar.ANIMATION_MODE_SLIDE

            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}