package com.ahmeturunveren.movieapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahmeturunveren.movieapp.R
import com.ahmeturunveren.movieapp.data.model.movie.MovieModel
import com.ahmeturunveren.movieapp.databinding.FragmentSearchScreenBinding
import com.ahmeturunveren.movieapp.ui.search.adapter.SearchAdapter
import com.ahmeturunveren.movieapp.util.extensions.click
import com.ahmeturunveren.movieapp.util.extensions.gone
import com.ahmeturunveren.movieapp.util.extensions.toastMessage
import com.ahmeturunveren.movieapp.util.extensions.visible
import com.ahmeturunveren.movieapp.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchScreen : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding
    private val searchViewModel: SearchVM by viewModels()
    private val searchAdapter by lazy { SearchAdapter(onSearchClick = ::onSearchClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search()
        initObserve()
        clickCancelButton()
        clickClearButton()
        clickBackButton()
    }

    private fun search() {
        var job: Job? = null
        binding.searchEditText.addTextChangedListener { editable ->
            // cancel the previous background task
            job?.cancel()
            job = MainScope().launch {
                binding.cancelButton.isVisible = editable.toString().isNotEmpty()
                delay(500L)
                if (editable.toString().isNotEmpty()) {
                    binding.searchRecyclerview.visible() // Makes the gone recycler view visible
                    searchViewModel.searchMovies(editable.toString())
                }
            }
        }
    }

    private fun initObserve() {
        searchViewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        initAdapter(it)
                    }
                }
                is Resource.Error->{
                    requireContext().toastMessage(getString(R.string.resource_error_message))
                }

                is Resource.Loading->{}

                else -> {
                    requireContext().toastMessage(response.message.orEmpty())
                }
            }
        }
    }

    private fun initAdapter(list: List<MovieModel>) {
        binding.searchRecyclerview.adapter = searchAdapter
        searchAdapter.submitList(list)
    }

    private fun clickCancelButton(){
        binding.cancelButton.click {
            with(binding){
                cancelButton.gone()
                searchRecyclerview.gone()
                searchEditText.setText("")
            }
            requireView().hideKeyboard()
        }
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun clickClearButton(){
        with(binding){
            clearButton.click {
                searchEditText.setText("")
            }
        }
    }

    private fun clickBackButton(){
        binding.searchToolbar.customToolbar.setNavigationOnClickListener {
           val action = SearchScreenDirections.actionSearchScreenToPopularMovieScreen()
            findNavController().navigate(action)
        }
    }

    private fun onSearchClick(movie: MovieModel) {
        val action = SearchScreenDirections.actionSearchScreenToDetailScreen(movie)
        findNavController().navigate(action)
    }
}