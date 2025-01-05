package com.example.movieshopapp.data.repo

import com.example.movieshopapp.data.datasource.MoviesDataSource
import com.example.movieshopapp.data.entity.Movies

class MoviesRepository(var moviesDataSource: MoviesDataSource) {

    suspend fun loadAllMovies() : List<Movies> = moviesDataSource.loadAllMovies()

    suspend fun addMovieToCart(name:String, image:String, price:Int, category:String, rating:Double, year:Int, director:String, description:String, orderAmount:Int, userName:String) = moviesDataSource.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
}