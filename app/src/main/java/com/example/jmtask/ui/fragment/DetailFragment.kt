package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialSharedAxis

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val bottomNavView by lazy { activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView) }

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        changeBottomNavVisibility(isVisible = false)

        requireActivity().onBackPressedDispatcher.addCallback {
            changeBottomNavVisibility(isVisible = true)
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        Log.d("NavController", args.movie.toString())


    }

    private fun changeBottomNavVisibility(isVisible: Boolean) {
        val animResource = if (isVisible) R.anim.slide_up else R.anim.slide_down
        val slideDownAnim = AnimationUtils.loadAnimation(requireContext(), animResource)
        bottomNavView?.startAnimation(slideDownAnim)

        slideDownAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                bottomNavView?.isVisible = isVisible
            }

            override fun onAnimationRepeat(p0: Animation?) {}

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}