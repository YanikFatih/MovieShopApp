package com.example.movieshopapp.ui.screen

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.ui.component.FilterBox
import com.example.movieshopapp.ui.theme.ButtonColor
import com.example.movieshopapp.ui.theme.InfoBoxColor
import com.example.movieshopapp.ui.theme.InfoBoxColorDark
import com.example.movieshopapp.ui.theme.MainColor
import com.example.movieshopapp.ui.theme.MainColorDark
import com.example.movieshopapp.ui.theme.TextColor
import com.example.movieshopapp.ui.theme.TextColorDark
import com.example.movieshopapp.ui.viewmodel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel, darkTheme:Boolean = isSystemInDarkTheme()) {
    val moviesList = homePageViewModel.moviesList.observeAsState(listOf())

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val rowScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        homePageViewModel.loadAllMovies()
    } //if any change occures in list, the updated list can be observed when turn back to homepage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { //multi color app name
                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = ButtonColor)) {
                            append(text = "Movie")
                        }
                        withStyle(style = SpanStyle(color = if(darkTheme) TextColorDark else TextColor)) {
                            append("Shop")
                        }
                    })
                },
                colors = TopAppBarColors(
                    containerColor = if(darkTheme) MainColorDark else MainColor,
                    scrolledContainerColor = MainColor,
                    navigationIconContentColor = TextColor,
                    titleContentColor = TextColor,
                    actionIconContentColor = TextColor,
                )
            )
        },
        containerColor = if(darkTheme) MainColorDark else MainColor,
        bottomBar = {
            BottomAppBar(
                content = {
                    NavigationBarItem(
                        selected = false,
                        label = { Text(stringResource(R.string.nav_item_home), color = TextColorDark) },
                        onClick = {

                        },
                        icon = { Icon(painterResource(R.drawable.home_icon), "", Modifier.size(28.dp), TextColorDark) }
                    )
                    NavigationBarItem(
                        selected = false,
                        label = { Text(stringResource(R.string.nav_item_favorites), color = TextColorDark) },
                        onClick = {
                            navController.navigate("favoritesPage")
                        },
                        icon = { Icon(painterResource(R.drawable.add_favourites_icon), "", Modifier.size(28.dp), TextColorDark) }
                    )
                    NavigationBarItem(
                        selected = false,
                        label = { Text(stringResource(R.string.nav_item_cart), color = TextColorDark) },
                        onClick = {
                            navController.navigate("movieCartPage")
                        },
                        icon = { Icon(painterResource(R.drawable.cart_icon), "", Modifier.size(28.dp), TextColorDark) }
                    )
                },
                modifier = Modifier.height(90.dp).clip(shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)),
                containerColor = ButtonColor,
                contentColor = TextColorDark,
            ) //bottom app bar
        },

    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier.horizontalScroll(rowScrollState).height((screenHeight/10).dp).padding(top = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                FilterBox(screenWidth/3, stringResource(R.string.top_rated_filter), darkTheme)
                FilterBox(screenWidth/3,stringResource(R.string.action_filter), darkTheme)
                FilterBox(screenWidth/3,stringResource(R.string.drama_filter), darkTheme)
                FilterBox(screenWidth/3,stringResource(R.string.fantastic_filter), darkTheme)
                FilterBox(screenWidth/3,stringResource(R.string.science_fiction_filter), darkTheme) //filter box components (not dynamic yet)
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize().padding(all = 0.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(
                    count = moviesList.value.count(),
                    itemContent = {
                        val movie = moviesList.value[it] //movie item
                        val imageUrl = "http://kasimadalan.pe.hu/movies/images/${movie.image}"//movie image url
                        Card(
                            modifier = Modifier.padding(start = 2.dp, end = 2.dp, top = 2.dp, bottom = 10.dp),
                            shape = RoundedCornerShape(0)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().background(color = if(darkTheme) MainColorDark else MainColor).clickable {
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
                                ) //movie image
                                Text(movie.name, color = if(darkTheme) TextColorDark else TextColor) //movie name
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(start = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                )
                                {
                                    Box(
                                        modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/27).dp).width((screenWidth/7).dp).background(color = if(darkTheme) InfoBoxColorDark else InfoBoxColor)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painterResource(R.drawable.year_icon),"", modifier = Modifier.size(12.dp,12.dp), if(darkTheme) TextColorDark else TextColor)
                                            Text("${movie.year}", color = if(darkTheme) TextColorDark else TextColor, fontSize = 12.sp)
                                        }
                                    }
                                    Box(
                                        modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/27).dp).width((screenWidth/8).dp).background(color = if(darkTheme) InfoBoxColorDark else InfoBoxColor)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(painterResource(R.drawable.star_icon),"", modifier = Modifier.size(12.dp,12.dp), Color.Yellow)
                                            Text("${movie.rating}", color = if(darkTheme) TextColorDark else TextColor, fontSize = 12.sp)
                                        }
                                    }
                                }//movie rating and year row
                            }
                        }
                    }
                )
            }
        }

    }

}