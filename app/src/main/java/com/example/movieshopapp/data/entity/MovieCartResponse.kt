package com.example.movieshopapp.data.entity

import android.database.Observable
import androidx.lifecycle.MutableLiveData
import retrofit2.Response

data class MovieCartResponse(var movie_cart:List<MovieCart>) {
}