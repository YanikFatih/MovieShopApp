package com.example.movieshopapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.ui.viewmodel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel) {
    val moviesList = homePageViewModel.moviesList.observeAsState(listOf())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        homePageViewModel.loadAllMovies()
    } //if any change occures in list, the updated list can be observed when turn back to homepage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { //sonradan arama kısmı yapılabilir
                    Text("Movies")
                },
                actions = {
                    Icon(painterResource(R.drawable.search_icon),"")
                    IconButton(onClick = {
                        navController.navigate("movieCartPage")
                    }) {
                        Icon(painterResource(R.drawable.cart_icon),"")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            items(
                count = moviesList.value.count(),
                itemContent = {
                    val movie = moviesList.value[it]
                    val imageUrl = "http://kasimadalan.pe.hu/movies/images/${movie.image}"//movie image url
                    Card(
                        modifier = Modifier.padding(all = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().clickable {
                                val movieJson = Gson().toJson(movie)
                                navController.navigate("movieDetailPage/$movieJson")
                                //sending movie data to detail page
                            }
                        ) {
                            //movie image
                            GlideImage(
                                imageModel = imageUrl,
                                modifier = Modifier.size(200.dp, 300.dp)
                            )
                            //movie name
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //movie category
                                Text(text = movie.name)
                                //movie rating
                                Text(text = "${movie.rating}")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //movie price
                                Text(text = "${movie.price}")
                                //add cart button
                            }
                        }
                    }
                }
            )
        }
    }

}