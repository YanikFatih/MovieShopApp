package com.example.movieshopapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.ui.theme.InfoBoxColor
import com.example.movieshopapp.ui.theme.MainColor
import com.example.movieshopapp.ui.theme.TextColor
import com.example.movieshopapp.ui.viewmodel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel) {
    val moviesList = homePageViewModel.moviesList.observeAsState(listOf())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

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
                },
                colors = TopAppBarColors(
                    containerColor = MainColor,
                    scrolledContainerColor = MainColor,
                    navigationIconContentColor = TextColor,
                    titleContentColor = TextColor,
                    actionIconContentColor = TextColor,
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    NavigationBarItem(
                        selected = false,
                        label = { Text("Home", color = TextColor) },
                        onClick = {},
                        icon = { Icon(painterResource(R.drawable.home_icon), "", Modifier.size(28.dp), TextColor) }
                    )
                    NavigationBarItem(
                        selected = false,
                        label = { Text("Kategoriler", color = TextColor) },
                        onClick = {},
                        icon = { Icon(painterResource(R.drawable.category_icon), "", Modifier.size(28.dp), TextColor) }
                    )
                    NavigationBarItem(
                        selected = false,
                        label = { Text("Siz", color = TextColor) },
                        onClick = {},
                        icon = { Icon(painterResource(R.drawable.profile_icon), "", Modifier.size(28.dp), TextColor) }
                    )
                    NavigationBarItem(
                        selected = false,
                        label = { Text("Sepet", color = TextColor) },
                        onClick = {},
                        icon = { Icon(painterResource(R.drawable.cart_icon), "", Modifier.size(28.dp), TextColor) }
                    )
                },
                modifier = Modifier.height(90.dp),
                containerColor = MainColor,
                contentColor = TextColor
            )
        },
        containerColor = MainColor
    ) { paddingValues ->
        /*LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            items(
                count = moviesList.value.count(),
                itemContent = {
                    val movie = moviesList.value[it]
                    val imageUrl = "http://kasimadalan.pe.hu/movies/images/${movie.image}"//movie image url
                    Card(
                        modifier = Modifier.padding(3.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().clickable {
                                val movieJson = Gson().toJson(movie)
                                navController.navigate("movieDetailPage/$movieJson")
                                //sending movie data to detail page
                            },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            GlideImage(
                                imageModel = imageUrl,
                                modifier = Modifier.size(100.dp,150.dp)
                            )
                            Column(
                                modifier = Modifier.fillMaxSize().padding(10.dp),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(text = movie.name)
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(text = movie.category)
                                    Text(text = "${movie.rating}")
                                }
                            }
                        }
                    }
                }
            )
        }*/
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
                        modifier = Modifier.padding(start = 2.dp, end = 2.dp, top = 2.dp, bottom = 10.dp),
                        shape = RoundedCornerShape(0)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().background(color = MainColor).clickable {
                                val movieJson = Gson().toJson(movie)
                                navController.navigate("movieDetailPage/$movieJson")
                                //sending movie data to detail page
                            },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //movie image
                            GlideImage(
                                imageModel = imageUrl,
                                modifier = Modifier.clip(shape = RoundedCornerShape(15.dp)).size(180.dp, 270.dp)
                            )
                            Text(movie.name, color = TextColor)
                            Row(
                                modifier = Modifier.fillMaxSize().padding(start = 2.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/27).dp).width((screenWidth/8).dp).background(color = InfoBoxColor)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("${movie.year}", color = TextColor, fontSize = 12.sp)
                                    }
                                }
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/27).dp).width((screenWidth/8).dp).background(color = InfoBoxColor)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("${movie.rating}", color = TextColor, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }

}