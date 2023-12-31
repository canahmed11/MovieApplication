package com.ahmeturunveren.movieapp.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmeturunveren.movieapp.R
import com.ahmeturunveren.movieapp.data.model.movie.MovieModel
import com.ahmeturunveren.movieapp.databinding.FragmentPopularMovieScreenBinding
import com.ahmeturunveren.movieapp.ui.movies.adapter.PopularMovieAdapter
import com.ahmeturunveren.movieapp.util.extensions.gone
import com.ahmeturunveren.movieapp.util.extensions.toastMessage
import com.ahmeturunveren.movieapp.util.extensions.visible
import com.ahmeturunveren.movieapp.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieScreen : Fragment() {

    private lateinit var binding: FragmentPopularMovieScreenBinding
    private val popularMovieViewModel: PopularMovieVM by viewModels()
    private val popularMovieAdapter by lazy { PopularMovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPopularMovieScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        popularMovieViewModel.getMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    with(binding){
                        progresbar.gone()
                        popularMovieTitle.text = getString(R.string.popular_movie_title)
                    }
                    if (!response.data.isNullOrEmpty()) initAdapter(response.data)
                }

                is Resource.Error ->{
                    with(binding){
                        progresbar.visible()
                        popularMovieTitle.gone()
                    }
                    requireContext().toastMessage(getString(R.string.resource_error_message))
                }

                is Resource.Loading->{
                    binding.progresbar.visible()
                }

                else -> {
                    requireContext().toastMessage(response.message.orEmpty())
                }
            }
        }
    }

    private fun initAdapter(list: List<MovieModel>) {
        binding.popularMovieRecyclerview.adapter = popularMovieAdapter
        popularMovieAdapter.submitList(list)
    }
}