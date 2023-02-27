package com.example.appwithretrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

data class DogImageResponse(
    val message: String,
    val status: String
)

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage() : DogImageResponse
}