package com.example.movieshopapp.retrofit

import android.database.Observable
import com.example.movieshopapp.data.entity.CRUDResponse
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.entity.MovieCartResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MovieCartDao {
    //http://kasimadalan.pe.hu/movies/getMovieCart.php

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMovieCart(@Field("userName") userName:String) : Response<MovieCartResponse>

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovieCart(@Field("cartId") cartId:Int, @Field("userName") userName: String) : CRUDResponse

    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun addMovieToCart(@Field("name") name:String,
                               @Field("image") image:String,
                               @Field("price") price:Int,
                               @Field("category") category:String,
                               @Field("rating") rating:Double,
                               @Field("year") year:Int,
                               @Field("director") director:String,
                               @Field("description") description:String,
                               @Field("orderAmount") orderAmount:Int,
                               @Field("userName") userName:String) : CRUDResponse
}