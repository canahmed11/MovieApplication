package com.ahmeturunveren.movieapp.data.source.remote

import com.ahmeturunveren.movieapp.data.model.login.CreateTokenModel
import com.ahmeturunveren.movieapp.data.model.login.ValidationRequestModel
import com.ahmeturunveren.movieapp.data.model.login.ValidationResponseModel
import com.ahmeturunveren.movieapp.data.model.movie.Movies
import com.ahmeturunveren.movieapp.util.constants.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieApi {

    @GET(Constants.SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") pageNumber: Int = 1,
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): Response<Movies>

    @GET(Constants.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): Response<Movies>

    @GET(Constants.CREATE_TOKEN)
    suspend fun createToken(
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): Response<CreateTokenModel>

    @POST(Constants.VALIDATE_WITH_LOGIN)
    suspend fun validateWithLogin(
        @Query("api_key") apiKey: String,
        @Body validationRequestModel: ValidationRequestModel,
    ): Response<ValidationResponseModel>
}