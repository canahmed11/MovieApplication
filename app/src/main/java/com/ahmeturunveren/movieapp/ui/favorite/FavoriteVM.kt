package com.ahmeturunveren.movieapp.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmeturunveren.movieapp.data.model.movie.FavoriteModel
import com.ahmeturunveren.movieapp.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(private val movieRepo:MovieRepository): ViewModel() {

    val getFavoriteMovies : MutableLiveData<List<FavoriteModel>> = MutableLiveData()

    init {
        getFavorites()
    }

    private fun getFavorites() = viewModelScope.launch {
        getFavoriteMovies.postValue(movieRepo.getMovieToFavorite())
    }

    fun deleteMovieFromFavorites(favId:Int) = viewModelScope.launch {
        movieRepo.deleteMovieFromFavorites(favId)
        getFavorites()
    }
}