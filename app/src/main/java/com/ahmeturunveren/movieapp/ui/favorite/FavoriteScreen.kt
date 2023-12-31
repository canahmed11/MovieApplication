package com.ahmeturunveren.movieapp.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahmeturunveren.movieapp.R
import com.ahmeturunveren.movieapp.databinding.FragmentFavoriteScreenBinding
import com.ahmeturunveren.movieapp.ui.favorite.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteScreen : Fragment() {

    private lateinit var binding: FragmentFavoriteScreenBinding
    private val favAdapter by lazy { FavoriteAdapter() }
    private val favoriteViewModel: FavoriteVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserve()
        clickBackButton()
    }

    private fun initAdapter(){
        binding.favRecyclerview.adapter = favAdapter
        favAdapter.onDeleteClick = {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_message))
                .setPositiveButton(getString(R.string.alert_dialog_positive)){ _ , _, ->
                    favoriteViewModel.deleteMovieFromFavorites(it)
                }
                .setNegativeButton(getString(R.string.alert_dialog_negative),null)
                .show()
        }
    }

    private fun initObserve(){
        favoriteViewModel.getFavoriteMovies.observe(viewLifecycleOwner){
            favAdapter.submitList(it)
        }
    }

    private fun clickBackButton(){
        binding.favToolbar.customToolbar.setNavigationOnClickListener {
            val action = FavoriteScreenDirections.actionFavoriteScreenToSearchScreen()
            findNavController().navigate(action)
        }
    }
}