package com.example.movieshopapp.di

import com.example.movieshopapp.data.datasource.MovieCartDataSource
import com.example.movieshopapp.data.datasource.MoviesDataSource
import com.example.movieshopapp.data.repo.MovieCartRepository
import com.example.movieshopapp.data.repo.MoviesRepository
import com.example.movieshopapp.retrofit.ApiUtils
import com.example.movieshopapp.retrofit.MovieCartDao
import com.example.movieshopapp.retrofit.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provieMoviesRepository(moviesDataSource: MoviesDataSource) : MoviesRepository {
        return MoviesRepository(moviesDataSource)
    }

    @Provides
    @Singleton
    fun provideMoviesDataSource(moviesDao: MoviesDao) : MoviesDataSource {
        return MoviesDataSource(moviesDao)
    }

    @Provides
    @Singleton
    fun provideMoviesDao() : MoviesDao {
        return ApiUtils.getMoviesDao()
    }

    @Provides
    @Singleton
    fun provideMovieCartRepository(movieCartDataSource: MovieCartDataSource) : MovieCartRepository {
        return MovieCartRepository(movieCartDataSource)
    }

    @Provides
    @Singleton
    fun provieMovieCartDataSource(movieCartDao: MovieCartDao) : MovieCartDataSource {
        return MovieCartDataSource(movieCartDao)
    }

    @Provides
    @Singleton
    fun provideMovieCartDao() : MovieCartDao {
        return ApiUtils.getMovieCartDao()
    }
}