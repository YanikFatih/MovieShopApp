package com.example.movieshopapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieshopapp.data.entity.Favorites
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.repo.FavoritesRepository
import com.example.movieshopapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(var moviesRepository: MoviesRepository, var favoritesRepository: FavoritesRepository) : ViewModel() {

    init {
        getFavorites()
    }

    fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            moviesRepository.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
        }
    }

    var movieCartList = MutableLiveData<List<MovieCart>>()
    fun getMovieCart(userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                movieCartList.value = moviesRepository.getMovieCart(userName)
            }catch (e:Exception) {

            }

        }
    }

    fun deleteMovieCart(cartId:Int, userName:String) {
        CoroutineScope(Dispatchers.Main).launch {
            moviesRepository.deleteMovieCart(cartId, userName)
            getMovieCart(userName) //getting cart again after delete operation
        }
    }

    fun addToFvorites(name:String, image:String, category:String, year:Int, director:String, description:String) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.addToFavorites(name, image, category, year, director, description)
        }
    }

    var favoritesList = MutableLiveData<List<Favorites>>()
    fun getFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesList.value = favoritesRepository.getFavorites()
        }
    }

    fun deleteFromFavorites(id:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.deleteFromFavorites(id)
            getFavorites()
        }
    }
}