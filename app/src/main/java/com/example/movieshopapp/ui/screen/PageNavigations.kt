package com.example.movieshopapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.ui.viewmodel.HomePageViewModel
import com.example.movieshopapp.ui.viewmodel.MovieCartViewModel
import com.example.movieshopapp.ui.viewmodel.MovieDetailViewModel
import com.google.gson.Gson

@Composable
fun PageNavigations(homePageViewModel: HomePageViewModel,
                    movieDetailViewModel: MovieDetailViewModel,
                    movieCartViewModel: MovieCartViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homepage") {
        composable("homepage") {
            HomePage(navController = navController, homePageViewModel = homePageViewModel)
        }
        composable("movieDetailPage/{movie}", arguments = listOf(navArgument("movie") { type = NavType.StringType })) {
            val json = it.arguments?.getString("movie")
            val obj = Gson().fromJson(json, Movies::class.java)
            MovieDetailPage(navController = navController, recievedMovide = obj, movieDetailViewModel = movieDetailViewModel)
        }
        composable("movieCartPage") {
            MovieCartPage(navController = navController, movieCartViewModel = movieCartViewModel)
        }
    }
}