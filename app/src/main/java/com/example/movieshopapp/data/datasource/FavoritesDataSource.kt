package com.example.movieshopapp.data.datasource

import com.example.movieshopapp.data.entity.Favorites
import com.example.movieshopapp.room.FavoritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesDataSource(var favoritesDao: FavoritesDao) {
    suspend fun  getFavorites() : List<Favorites> = withContext(Dispatchers.IO) {
        return@withContext favoritesDao.getFavorites()
    }

    suspend fun addToFavorites(name:String, image:String, category:String, year:Int, director:String, description:String) {
        val movieAddedToFavorites = Favorites(0, name, image, category, year, director, description)
        favoritesDao.addToFavorites(movieAddedToFavorites)
    }

    suspend fun deleteFromFavorites(id:Int) {
        val movieDeletedFromFavorites = Favorites(id, "","","", 0, "", "")
        favoritesDao.deleteFromFavorites(movieDeletedFromFavorites)
    }

    suspend fun search(searchString: String) : List<Favorites> = withContext(Dispatchers.IO) {
        return@withContext favoritesDao.search(searchString)
    }
}