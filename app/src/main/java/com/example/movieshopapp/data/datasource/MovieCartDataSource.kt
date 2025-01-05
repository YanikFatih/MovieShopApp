package com.example.movieshopapp.data.datasource

import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.retrofit.MovieCartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieCartDataSource(var movieCartDao: MovieCartDao) {

    suspend fun getMovieCart(userName:String) : List<MovieCart> = withContext(Dispatchers.IO) {
        return@withContext movieCartDao.getMovieCart(userName).movie_cart
    }

    suspend fun deleteMovieCart(cartId:Int, userName: String) {
        movieCartDao.deleteMovieCart(cartId, userName)
    }
}