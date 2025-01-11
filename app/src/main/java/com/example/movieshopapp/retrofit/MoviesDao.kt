package com.example.movieshopapp.retrofit

import android.credentials.CredentialDescription
import androidx.room.Dao
import androidx.room.Insert
import com.example.movieshopapp.data.entity.CRUDResponse
import com.example.movieshopapp.data.entity.Favorites
import com.example.movieshopapp.data.entity.MovieCartResponse
import com.example.movieshopapp.data.entity.MoviesResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MoviesDao {
    //http://kasimadalan.pe.hu/movies/getAllMovies.php
    //http://kasimadalan.pe.hu/ -> base url
    //movies/getAllMovies.php -> api url

    @GET("movies/getAllMovies.php")
    suspend fun loadAllMovies() : MoviesResponse

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

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMovieCart(@Field("userName") userName:String) : MovieCartResponse

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovieCart(@Field("cartId") cartId:Int, @Field("userName") userName: String) : CRUDResponse

}