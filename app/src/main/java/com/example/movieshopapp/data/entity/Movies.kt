package com.example.movieshopapp.data.entity

data class Movies(var id:Int ,
                  var name:String,
                  var image:String,
                  var price:Int,
                  var category:String,
                  var rating:Double,
                  var year:Int,
                  var director:String,
                  var description:String) {
}