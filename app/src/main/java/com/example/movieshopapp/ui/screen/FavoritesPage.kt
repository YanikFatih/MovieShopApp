package com.example.movieshopapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.ui.theme.ButtonColor
import com.example.movieshopapp.ui.theme.InfoBoxColor
import com.example.movieshopapp.ui.theme.InfoBoxColorDark
import com.example.movieshopapp.ui.theme.MainColor
import com.example.movieshopapp.ui.theme.MainColorDark
import com.example.movieshopapp.ui.theme.TextColor
import com.example.movieshopapp.ui.theme.TextColorDark
import com.example.movieshopapp.ui.viewmodel.FavoritesPageViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesPage(navController: NavController, favoritesPageViewModel: FavoritesPageViewModel, darkTheme:Boolean = isSystemInDarkTheme()) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val isSeacrhing = remember { mutableStateOf(false) }
    val tfSearch = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val favoritesList = favoritesPageViewModel.favoritesList.observeAsState(listOf())

    LaunchedEffect(key1 = true) {
        favoritesPageViewModel.getFavorites() //getting favorite movies from viewModel
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("homepage") //back icon navigation
                        },
                        colors = IconButtonColors(
                            contentColor = ButtonColor,
                            containerColor = if(darkTheme) MainColorDark else MainColor,
                            disabledContentColor = TextColor,
                            disabledContainerColor = TextColor
                        )
                    ) {
                        Icon(painterResource(R.drawable.go_back_icon),"")
                    }
                },
                title = {
                    if(isSeacrhing.value) {
                        TextField(
                            value = tfSearch.value,
                            onValueChange = {
                                tfSearch.value = it
                                favoritesPageViewModel.search(it)
                            },
                            label = { Text(stringResource(R.string.tf_label_search)) },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedLabelColor = if(darkTheme) TextColorDark else TextColor,
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    } else {
                        Text("My Favorites", )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = if(darkTheme) MainColorDark else MainColor,
                    scrolledContainerColor = MainColor,
                    navigationIconContentColor = TextColor,
                    titleContentColor = if(darkTheme) TextColorDark else TextColor,
                    actionIconContentColor = TextColor,
                ),
                actions = {
                    if(isSeacrhing.value) {
                        IconButton(onClick = {
                            isSeacrhing.value = false
                            tfSearch.value = ""
                        },
                            colors = IconButtonColors(
                                contentColor = if(darkTheme) TextColorDark else TextColor,
                                containerColor = if(darkTheme) MainColorDark else MainColor,
                                disabledContentColor = TextColor,
                                disabledContainerColor = TextColor
                            )
                        ) {
                            Icon(painterResource(R.drawable.close_icon), "")
                        }
                    } else {
                        IconButton(onClick = {
                            isSeacrhing.value = true
                        },
                            colors = IconButtonColors(
                                contentColor = if(darkTheme) TextColorDark else TextColor,
                                containerColor = if(darkTheme) MainColorDark else MainColor,
                                disabledContentColor = TextColor,
                                disabledContainerColor = TextColor
                            )
                        ) {
                            Icon(painterResource(R.drawable.search_icon), "")
                        }
                    }
                }
            )
        },
        containerColor = if(darkTheme) MainColorDark else MainColor,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.background(color = ButtonColor).clip(shape = RoundedCornerShape(10.dp)))
        }
    ) { paddingValues ->
        LazyColumn( // to show list of favorites
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                count = favoritesList.value.count(),
                itemContent = {
                    var favoriteMovie = favoritesList.value[it] //favorite item
                    val imageUrl = "http://kasimadalan.pe.hu/movies/images/${favoriteMovie.image}" //image url

                    Card(
                        modifier = Modifier.padding(all = 7.dp).shadow(elevation = 5.dp, spotColor = MainColorDark, shape = RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().background(color = if(darkTheme) InfoBoxColorDark else InfoBoxColor),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GlideImage( // item image
                                imageModel = imageUrl,
                                modifier = Modifier.size(100.dp, 150.dp).padding(5.dp).clip(shape = RoundedCornerShape(15.dp)),
                            )
                            Column(
                                modifier = Modifier.fillMaxHeight().padding(start = 10.dp),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Row() {
                                    Icon(painterResource(R.drawable.movie_camera_icon),"", modifier = Modifier.size((screenWidth/12).dp, (screenHeight/19).dp).padding(end = 8.dp), if(darkTheme) TextColorDark else TextColor)
                                    Column {
                                        Text(text = favoriteMovie.name, color = if(darkTheme) TextColorDark else TextColor, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                                        Text(text = " ${favoriteMovie.category}, ${favoriteMovie.year}", color = if(darkTheme) TextColorDark else TextColor, fontSize = 12.sp)
                                    }
                                }
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/25).dp).width((screenWidth/2.6).dp).background(color = if(darkTheme) MainColorDark else MainColor)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(painterResource(R.drawable.director_icon),"", modifier = Modifier.size(14.dp,14.dp).padding(end = 3.dp), if(darkTheme) TextColorDark else TextColor)
                                        Text(text = favoriteMovie.director, color = if(darkTheme) TextColorDark else TextColor, fontSize = 12.sp)
                                    }
                                }
                            }//column showing movie name, category, year and director
                            Column(
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.fillMaxSize().padding(10.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            val snackbar = snackbarHostState.showSnackbar(
                                                message = "Remove ${favoriteMovie.name} from favorites?",
                                                actionLabel = "YES",
                                            )
                                            if (snackbar == SnackbarResult.ActionPerformed) {
                                                favoritesPageViewModel.deleteFromFavorites(favoriteMovie.id)
                                            }
                                        }
                                    }
                                ) {
                                    Icon(painterResource(R.drawable.delete_icon), "", modifier = Modifier.size(24.dp,24.dp) ,ButtonColor)
                                }
                            }//delete button part
                        }
                    }
                }
            )
        }
    }
}