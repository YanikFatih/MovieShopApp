package com.example.movieshopapp.retrofit

class ApiUtils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getMoviesDao() : MoviesDao {
            return RetrofitClient.getClient(BASE_URL).create(MoviesDao::class.java)
        } //to access MoviesDao

        fun getMovieCartDao() : MovieCartDao {
            return RetrofitClient.getClient(BASE_URL).create(MovieCartDao::class.java)
        }
    }
}