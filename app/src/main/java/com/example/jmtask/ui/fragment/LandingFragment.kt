package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentLandingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialSharedAxis

class LandingFragment : Fragment(R.layout.fragment_landing) {

    private var _binding: FragmentLandingBinding? = null
    private val binding: FragmentLandingBinding get() = _binding!!
    private val bottomNavView by lazy { activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLandingBinding.bind(view)
        binding.letsGoBtn.setOnClickListener {
            showBottomNav()
            findNavController().navigate(R.id.action_landingFragment_to_homeFragment)
        }
    }

    private fun showBottomNav() {

        val slideDownAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        bottomNavView?.startAnimation(slideDownAnim)

        slideDownAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                bottomNavView?.isVisible = true
            }

            override fun onAnimationRepeat(p0: Animation?) {}

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}