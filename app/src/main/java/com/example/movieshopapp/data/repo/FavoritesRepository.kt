package com.example.movieshopapp.data.repo

import com.example.movieshopapp.data.datasource.FavoritesDataSource
import com.example.movieshopapp.data.entity.Favorites

class FavoritesRepository(var favoritesDataSource: FavoritesDataSource) {
    suspend fun getFavorites() : List<Favorites> = favoritesDataSource.getFavorites()

    suspend fun addToFavorites(name:String, image:String, category:String, year:Int, director:String, description:String) = favoritesDataSource.addToFavorites(name, image, category, year, director, description)

    suspend fun deleteFromFavorites(id:Int) = favoritesDataSource.deleteFromFavorites(id)

    suspend fun search(searchString: String) : List<Favorites> = favoritesDataSource.search(searchString)
}