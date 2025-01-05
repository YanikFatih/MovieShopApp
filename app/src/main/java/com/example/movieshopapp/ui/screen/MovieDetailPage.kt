package com.example.movieshopapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieshopapp.R
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.ui.viewmodel.MovieDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailPage(recievedMovide:Movies, movieDetailViewModel: MovieDetailViewModel) {
    val tMovieName = remember { mutableStateOf("") }
    val tMovieImg = remember { mutableStateOf("") }
    val tMoviePrice = remember { mutableIntStateOf(0) }
    val tMovieCategory = remember { mutableStateOf("") }
    val tMovieRating = remember { mutableDoubleStateOf(0.0) }
    val tMovieYear = remember { mutableIntStateOf(0) }
    val tMovieDirector = remember { mutableStateOf("") }
    val tMovieDescription = remember { mutableStateOf("") }

    val userName = remember { mutableStateOf("fatih_yanik") }

    val orderAmount = remember { mutableIntStateOf(1) }

    LaunchedEffect(key1 = true) {
        tMovieName.value = recievedMovide.name
        tMovieImg.value = recievedMovide.image
        tMoviePrice.value = recievedMovide.price
        tMovieCategory.value = recievedMovide.category
        tMovieRating.value = recievedMovide.rating
        tMovieYear.value = recievedMovide.year
        tMovieDirector.value = recievedMovide.director
        tMovieDescription.value = recievedMovide.description
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(tMovieName.value)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = "http://kasimadalan.pe.hu/movies/images/${tMovieImg.value}"

            GlideImage(
                imageModel = imageUrl,
                modifier = Modifier.size(200.dp, 300.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(tMovieName.value)
                Text(tMovieDirector.value)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₺${tMoviePrice.value}")
                Text("${tMovieRating.value}/10")
            }
            Text("${tMovieYear.value}")
            Text(tMovieCategory.value)
            Text(tMovieDescription.value)
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    var orderAmountInt = orderAmount.value
                    if (orderAmountInt > 1) {
                        orderAmountInt = orderAmountInt - 1
                        orderAmount.value = orderAmountInt
                    }
                }) {
                    Icon(painterResource(R.drawable.decrease_count_icon),"")
                }
                TextField(
                    value = "",
                    onValueChange = {
                        var orderAmountString = orderAmount.value.toString()
                        orderAmountString = it
                    },
                    label = {
                        Text(orderAmount.value.toString())
                    }
                )
                IconButton(onClick = {
                    var orderAmountInt = orderAmount.value
                    orderAmountInt = orderAmountInt + 1
                    orderAmount.value = orderAmountInt
                }) {
                    Icon(painterResource(R.drawable.add_one_icon),"")
                }
            }
            Button(
                onClick = {
                    movieDetailViewModel.addMovieToCart(recievedMovide.name, recievedMovide.image, recievedMovide.price, recievedMovide.category, recievedMovide.rating, recievedMovide.year, recievedMovide.director, recievedMovide.description, orderAmount.value, userName.value)
                }
            ) {
                Text("Add To Cart")
            }
        }
    }
}