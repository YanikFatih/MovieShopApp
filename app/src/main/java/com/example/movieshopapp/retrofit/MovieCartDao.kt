package com.example.movieshopapp.retrofit

import com.example.movieshopapp.data.entity.CRUDResponse
import com.example.movieshopapp.data.entity.MovieCartResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MovieCartDao {
    //http://kasimadalan.pe.hu/movies/getMovieCart.php

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMovieCart(@Field("userName") userName:String) : MovieCartResponse

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovieCart(@Field("cartId") cartId:Int, @Field("userName") userName: String) : CRUDResponse
}