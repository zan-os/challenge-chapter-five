package com.example.challenge_chapther_five.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_chapther_five.data.remote.ApiConfig
import com.example.challenge_chapther_five.data.response.MovieResponse
import com.example.challenge_chapther_five.data.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listMovie = MutableLiveData<List<ResultsItem>>()
    val listMovie: LiveData<List<ResultsItem>> = _listMovie

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    init {
        loadMovie()
    }

    private fun loadMovie() {
        _showLoading.value = true

        val client = ApiConfig.getApiService().getMovie()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val code = response.code()
                if (code == 200) {
                    _listMovie.value = response.body()?.results
                    Log.d("TAG", response.body()?.results.toString())
                    _showLoading.value = false
                } else {
                    Log.e("Failure", "onFailure: ${response.message()}")
                    _showLoading.value = false
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("Failure", "onFailure: ${t.message}")
                _showLoading.value = false
            }

        })
    }
}