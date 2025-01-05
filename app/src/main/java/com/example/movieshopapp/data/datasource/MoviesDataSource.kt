package com.example.movieshopapp.data.datasource

import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.retrofit.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSource(var moviesDao: MoviesDao) {

    suspend fun loadAllMovies() : List<Movies> = withContext(Dispatchers.IO) {
        return@withContext moviesDao.loadAllMovies().movies
    }

    suspend fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) {
        moviesDao.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
    }
}