package com.example.movieshopapp.retrofit

import android.credentials.CredentialDescription
import com.example.movieshopapp.data.entity.CRUDResponse
import com.example.movieshopapp.data.entity.MoviesResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MoviesDao {
    //http://kasimadalan.pe.hu/movies/getAllMovies.php
    //http://kasimadalan.pe.hu/ -> base url
    //movies/getAllMovies.php -> api url BUNLARI SABİTLER DİYE Bİ YERE EKLEYEBİLİRİZ İLERDE !!!!

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
}