package com.example.movieshopapp.data.datasource

import com.example.movieshopapp.data.entity.Favorites
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.retrofit.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MoviesDataSource(var moviesDao: MoviesDao) {

    suspend fun loadAllMovies() : List<Movies> = withContext(Dispatchers.IO) {
        return@withContext moviesDao.loadAllMovies().movies
    }

    suspend fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) {
        moviesDao.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
    }

    suspend fun getMovieCart(userName:String) : List<MovieCart> = withContext(Dispatchers.IO) {
        return@withContext moviesDao.getMovieCart(userName).movie_cart
    }

    suspend fun deleteMovieCart(cartId:Int, userName: String) {
        moviesDao.deleteMovieCart(cartId, userName)
    }
}