package com.example.movieshopapp

import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.movieshopapp.ui.screen.PageNavigations
import com.example.movieshopapp.ui.theme.MovieShopAppTheme
import com.example.movieshopapp.ui.viewmodel.FavoritesPageViewModel
import com.example.movieshopapp.ui.viewmodel.HomePageViewModel
import com.example.movieshopapp.ui.viewmodel.MovieCartViewModel
import com.example.movieshopapp.ui.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val homePageViewModel:HomePageViewModel by viewModels()
        val movieDetailViewModel:MovieDetailViewModel by viewModels()
        val movieCartViewModel:MovieCartViewModel by viewModels()
        val favoritesPageViewModel:FavoritesPageViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieShopAppTheme {
                PageNavigations(
                    homePageViewModel = homePageViewModel,
                    movieDetailViewModel = movieDetailViewModel,
                    movieCartViewModel = movieCartViewModel,
                    favoritesPageViewModel = favoritesPageViewModel)
            }
        }
    }
}
