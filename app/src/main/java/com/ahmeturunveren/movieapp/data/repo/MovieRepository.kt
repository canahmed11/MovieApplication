package com.ahmeturunveren.movieapp.data.repo

import com.ahmeturunveren.movieapp.data.model.movie.FavoriteModel
import com.ahmeturunveren.movieapp.data.model.movie.WatchlistModel
import com.ahmeturunveren.movieapp.data.source.local.MovieDao
import com.ahmeturunveren.movieapp.data.source.remote.MovieApi
import com.ahmeturunveren.movieapp.util.resource.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val movieDb: MovieDao
) {

    suspend fun searchMovie(query: String) = try {
        Resource.Success(api.searchMovie(query).body()?.results.orEmpty())
    } catch (e: Exception) {
        Resource.Error(e.message.orEmpty())
    }

    suspend fun getPopularMovie() = try {
        Resource.Success(api.getPopularMovie().body()?.results.orEmpty())
    } catch (e: Exception) {
        Resource.Error(e.message.orEmpty())
    }

    suspend fun addMovieToFavorite(movie:FavoriteModel){
        movieDb.addMovieToFavorite(movie)
    }

    suspend fun getMovieToFavorite():List<FavoriteModel>{
        return movieDb.getFavoriteMovie()
    }

    suspend fun deleteMovieFromFavorites(favId:Int){
        movieDb.deleteMovieFromFavorites(favId)
    }

    suspend fun addMovieToWatchlist(movie:WatchlistModel){
        movieDb.addMovieToWatchlist(movie)
    }

    suspend fun getMovieToWatchlist():List<WatchlistModel>{
        return movieDb.getWatchlistMovie()
    }

    suspend fun deleteMovieFromWatchlist(watchlistId:Int){
        movieDb.deleteMovieFromWatchlist(watchlistId)
    }
}