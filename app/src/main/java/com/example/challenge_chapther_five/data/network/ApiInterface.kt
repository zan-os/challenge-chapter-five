package com.example.challenge_chapther_five.data.network

import com.example.challenge_chapther_five.data.response.CreditsResponse
import com.example.challenge_chapther_five.data.response.DetailMovieResponse
import com.example.challenge_chapther_five.data.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("movie/popular?api_key=b92152edaafd72fd30cf3b638afb4d64")
    fun getMovie(): Call<MovieResponse>

    @GET("movie/{path}?api_key=b92152edaafd72fd30cf3b638afb4d64")
    fun getDetail(@Path("path") id: Int?): Call<DetailMovieResponse>

    @GET("movie/{path}/credits?api_key=b92152edaafd72fd30cf3b638afb4d64")
    fun getCast(@Path("path") id: Int?): Call<CreditsResponse>
}