package com.example.movieshopapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.repo.MovieCartRepository
import com.example.movieshopapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieCartViewModel @Inject constructor(var movieCartRepository: MovieCartRepository) : ViewModel() {
    var movieCartList = MutableLiveData<List<MovieCart>>()

    fun getMovieCart(userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            movieCartList.value = movieCartRepository.getMovieCart(userName)
        }
    }

    fun deleteMovieCart(cartId:Int, userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            movieCartRepository.deleteMovieCart(cartId, userName)
            getMovieCart(userName)
            //sildikten sonra tekrar yükleme işlemi olmalı ona bakılacak
        }
    }
}