package com.example.movieshopapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorites")
data class Favorites(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @NotNull var id:Int ,
                     @ColumnInfo(name = "name") @NotNull var name:String,
                     @ColumnInfo(name = "image") @NotNull var image:String,
                     @ColumnInfo(name = "category") @NotNull var category:String,
                     @ColumnInfo(name = "year") @NotNull var year:Int,
                     @ColumnInfo(name = "director") @NotNull var director:String,
                     @ColumnInfo(name = "description") @NotNull var description:String) {
}