package com.example.jmtask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jmtask.BuildConfig
import com.example.jmtask.R
import com.example.jmtask.databinding.FragmentDetailBinding
import com.example.jmtask.util.changeBottomNavVisibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialSharedAxis

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val bottomNavView by lazy { requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView) }

    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        changeBottomNavVisibility(isVisible = false, bottomNavView, requireContext())

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                changeBottomNavVisibility(isVisible = true, bottomNavView, requireContext())
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        Log.d("NavController", args.movie.toString())

        Glide.with(requireContext())
            .load(BuildConfig.IMG_BASE_URL + "/w500" + args.movie.poster_path)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.img)

        Glide.with(requireContext())
            .load(BuildConfig.IMG_BASE_URL + "/w500" + args.movie.backdrop_path)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.backgroundImg)

        binding.apply {
            heading.text = args.movie.original_title
            overview.text = args.movie.overview
            originalLanguage.text = args.movie.original_language
            englishTitle.text = args.movie.title
            rating.text = "${args.movie.vote_average.toString().substring(0, 3)} ⭐"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }
}