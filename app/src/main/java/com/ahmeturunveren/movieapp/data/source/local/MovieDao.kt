package com.ahmeturunveren.movieapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmeturunveren.movieapp.data.model.movie.FavoriteModel
import com.ahmeturunveren.movieapp.data.model.movie.WatchlistModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addMovieToFavorite(movie:FavoriteModel)

    @Query("SELECT * FROM favTable")
    suspend fun getFavoriteMovie():List<FavoriteModel>

    @Query("DELETE FROM favTable WHERE id=:favId")
    suspend fun deleteMovieFromFavorites(favId:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToWatchlist(movie:WatchlistModel)

    @Query("SELECT * FROM watchlistTable")
    suspend fun getWatchlistMovie():List<WatchlistModel>

    @Query("DELETE FROM watchlistTable WHERE id=:watchlistId")
    suspend fun deleteMovieFromWatchlist(watchlistId:Int)
}