package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentLandingBinding
import com.example.jmtask.ui.viewmodel.JMViewModel
import com.example.jmtask.util.ApiState
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LandingFragment : Fragment(R.layout.fragment_landing) {

    private var _binding: FragmentLandingBinding? = null
    private val binding: FragmentLandingBinding get() = _binding!!
    private val viewModel: JMViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLandingBinding.bind(view)

        lifecycleScope.launch(Dispatchers.IO) { getResp() }
    }

    private suspend fun getResp() {
        viewModel.getMovies()
            .flowWithLifecycle(lifecycle)
            .collectLatest {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is ApiState.Loading -> {
                            Log.d("RETRO", "Loading")
                        }

                        is ApiState.Error -> {
                            Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
                            Log.d("RETRO", it.errorMsg.toString())
                        }

                        is ApiState.Success -> {
                            Log.d("RETRO", it.data!!.toString())
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