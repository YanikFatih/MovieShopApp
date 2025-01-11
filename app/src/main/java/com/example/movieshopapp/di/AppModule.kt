package com.example.movieshopapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieshopapp.data.datasource.FavoritesDataSource
import com.example.movieshopapp.data.datasource.MovieCartDataSource
import com.example.movieshopapp.data.datasource.MoviesDataSource
import com.example.movieshopapp.data.repo.FavoritesRepository
import com.example.movieshopapp.data.repo.MovieCartRepository
import com.example.movieshopapp.data.repo.MoviesRepository
import com.example.movieshopapp.retrofit.ApiUtils
import com.example.movieshopapp.retrofit.MovieCartDao
import com.example.movieshopapp.retrofit.MoviesDao
import com.example.movieshopapp.room.Database
import com.example.movieshopapp.room.FavoritesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideFavoritesRepository(favoritesDataSource: FavoritesDataSource) : FavoritesRepository {
        return FavoritesRepository(favoritesDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoritesDataSource(favoritesDao: FavoritesDao) : FavoritesDataSource {
        return  FavoritesDataSource(favoritesDao)
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(@ApplicationContext context: Context) : FavoritesDao {
        val database = Room.databaseBuilder(context, Database::class.java, "movies.sqlite")
            .createFromAsset("movies.sqlite").build()
        return database.getFavoritesDao()
    }
}