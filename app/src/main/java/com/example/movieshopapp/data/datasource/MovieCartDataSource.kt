package com.example.movieshopapp.data.datasource

import android.database.Observable
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.retrofit.MovieCartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Objects

class MovieCartDataSource(var movieCartDao: MovieCartDao) {

    suspend fun getMovieCart(userName:String) : List<MovieCart> = withContext(Dispatchers.IO) {
            return@withContext movieCartDao.getMovieCart(userName).body()!!.movie_cart
    }

    suspend fun deleteMovieCart(cartId:Int, userName: String) {
        movieCartDao.deleteMovieCart(cartId, userName)
    }

    suspend fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) {
        movieCartDao.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
    }
}