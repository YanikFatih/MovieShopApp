package com.example.movieshopapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movieshopapp.data.entity.Favorites

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    suspend fun getFavorites() : List<Favorites>

    @Insert
    suspend fun addToFavorites(favorite:Favorites)

    @Delete
    suspend fun deleteFromFavorites(favorite: Favorites)

    @Query("SELECT * FROM favorites WHERE name LIKE '%'|| :searchString ||'%'")
    suspend fun search(searchString: String) : List<Favorites>
}