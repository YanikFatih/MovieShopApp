package com.example.movieshopapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.ui.viewmodel.MovieCartViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCartPage(navController: NavController, movieCartViewModel: MovieCartViewModel) {
    val movieCartList = movieCartViewModel.movieCartList.observeAsState(listOf())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val userName = "fatih_yanik"
    val movieNames = mutableListOf("")

    LaunchedEffect(key1 = true) {
        movieCartViewModel.getMovieCart(userName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { //sonradan arama kısmı yapılabilir
                    Text("My Cart")
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                count = movieCartList.value.count(),
                itemContent = {
                    val movieCart = movieCartList.value[it]
                    val imageUrl = "http://kasimadalan.pe.hu/movies/images/${movieCart.image}"
                    Card(
                        modifier = Modifier.padding(all = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            GlideImage(
                                imageModel = imageUrl,
                                modifier = Modifier.size(100.dp, 150.dp)
                            )
                            Text(text = movieCart.name)
                            Text(text = "${movieCart.orderAmount}")
                            IconButton(
                                onClick = {
                                    movieCartViewModel.deleteMovieCart(movieCart.cartId, userName)
                                }
                            ) {
                                Icon(painterResource(R.drawable.delete_icon), "")
                            }
                        }
                    }
                }
            )
        }
    }
}