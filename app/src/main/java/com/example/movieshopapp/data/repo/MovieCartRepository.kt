package com.example.movieshopapp.data.repo

import com.example.movieshopapp.data.datasource.MovieCartDataSource
import com.example.movieshopapp.data.entity.MovieCart

class MovieCartRepository(var movieCartDataSource: MovieCartDataSource) {
    suspend fun getMovieCart(userName:String) : List<MovieCart> = movieCartDataSource.getMovieCart(userName)

    suspend fun deleteMovieCart(cartId:Int, userName:String) = movieCartDataSource.deleteMovieCart(cartId, userName)
}