package com.example.movieshopapp.ui.viewmodel

import android.database.Observable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.data.repo.MovieCartRepository
import com.example.movieshopapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieCartViewModel @Inject constructor(var movieCartRepository: MovieCartRepository) : ViewModel() {
    var movieCartList = MutableLiveData<List<MovieCart>>()
    /*val movieCartList: LiveData<List<MovieCart>> get() = _movieCartList
    val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading*/
    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean> get() = _hasError

    init {
        getMovieCart("fatih_yanik")
    }

    fun getMovieCart(userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                movieCartList.value = movieCartRepository.getMovieCart(userName)
                _hasError.value = false
            }catch (e:Exception) {
                _hasError.value = true
            }
        }
    }

    fun deleteMovieCart(cartId:Int, userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            movieCartRepository.deleteMovieCart(cartId, userName)
            getMovieCart(userName) //to get movie cart after delete operation
        }
    }

    fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            movieCartRepository.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
        }
    }
}