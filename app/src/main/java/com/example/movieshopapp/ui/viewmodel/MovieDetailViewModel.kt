package com.example.movieshopapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieshopapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(var moviesRepository: MoviesRepository) : ViewModel() {
    fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            moviesRepository.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
        }
    }


}