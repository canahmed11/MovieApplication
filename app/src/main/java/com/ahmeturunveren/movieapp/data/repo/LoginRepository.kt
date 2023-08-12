package com.ahmeturunveren.movieapp.data.repo

import com.ahmeturunveren.movieapp.data.model.login.ValidationRequestModel
import com.ahmeturunveren.movieapp.data.source.remote.MovieApi
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api:MovieApi) {

    suspend fun getRequestToken() = api.createToken()

    suspend fun validateWithLogin(apiKey:String,validationRequestModel: ValidationRequestModel) =
        api.validateWithLogin(apiKey,validationRequestModel)
}