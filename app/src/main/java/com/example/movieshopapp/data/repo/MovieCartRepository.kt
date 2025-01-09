package com.example.movieshopapp.data.repo

import android.database.Observable
import com.example.movieshopapp.data.datasource.MovieCartDataSource
import com.example.movieshopapp.data.entity.MovieCart
import retrofit2.Response

class MovieCartRepository(var movieCartDataSource: MovieCartDataSource) {
    suspend fun getMovieCart(userName:String) : List<MovieCart> = movieCartDataSource.getMovieCart(userName)

    suspend fun deleteMovieCart(cartId:Int, userName:String) = movieCartDataSource.deleteMovieCart(cartId, userName)

    suspend fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) = movieCartDataSource.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
}