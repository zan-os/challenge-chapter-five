package com.example.challenge_chapther_five.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_chapther_five.data.network.ApiConfig
import com.example.challenge_chapther_five.data.response.CastItem
import com.example.challenge_chapther_five.data.response.CreditsResponse
import com.example.challenge_chapther_five.data.response.DetailMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailMovie = MutableLiveData<DetailMovieResponse>()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovie

    private val _castList = MutableLiveData<List<CastItem>>()
    val castList: LiveData<List<CastItem>> = _castList

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    fun getMovieDetail(id: Int) {
        val client = ApiConfig.getApiService().getDetail(id)
        client.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                val code = response.code()
                if (code == 200) {
                    _detailMovie.value = response.body()
                } else {
                    Log.e("Failure", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.e("Failure", "onFailure: ${t.message}")
            }
        })
    }

    fun getMovieCast(id: Int) {
        _showLoading.value = true
        val client = ApiConfig.getApiService().getCast(id)
        client.enqueue(object : Callback<CreditsResponse> {
            override fun onResponse(
                call: Call<CreditsResponse>,
                response: Response<CreditsResponse>
            ) {
                val code = response.code()
                if (code == 200) {
                    _showLoading.value = false
                    _castList.value = response.body()?.cast
                } else {
                    _showLoading.value = true
                    Log.e("Failure", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                _showLoading.value = true
                Log.e("Failure", "onFailure: ${t.message}")
            }
        })
    }
}