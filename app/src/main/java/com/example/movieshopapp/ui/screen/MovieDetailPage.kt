package com.example.movieshopapp.ui.screen

import androidx.collection.mutableObjectListOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.formatWithSkeleton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.data.entity.Movies
import com.example.movieshopapp.ui.theme.ButtonColor
import com.example.movieshopapp.ui.theme.InfoBoxColor
import com.example.movieshopapp.ui.theme.MainColor
import com.example.movieshopapp.ui.theme.TextColor
import com.example.movieshopapp.ui.viewmodel.MovieDetailViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailPage(navController: NavController, recievedMovide:Movies, movieDetailViewModel: MovieDetailViewModel) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val tMovieName = remember { mutableStateOf("") }
    val tMovieImg = remember { mutableStateOf("") }
    val tMoviePrice = remember { mutableIntStateOf(0) }
    val tMovieCategory = remember { mutableStateOf("") }
    val tMovieRating = remember { mutableDoubleStateOf(0.0) }
    val tMovieYear = remember { mutableIntStateOf(0) }
    val tMovieDirector = remember { mutableStateOf("") }
    val tMovieDescription = remember { mutableStateOf("") }

    var totalPrice = remember { mutableStateOf(recievedMovide.price) }

    val userName = "fatih_yanik"

    val orderAmount = remember { mutableIntStateOf(1) }

    val movieCartList = movieDetailViewModel.movieCartList.observeAsState(listOf())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val favoritesList = movieDetailViewModel.favoritesList.observeAsState(listOf())

    val isFavIconClicked = remember { mutableStateOf(false) }
    var size = movieCartList.value.size
    var moviesInCart = ArrayList<String>()
    var moviesInFavorites = ArrayList<String>()

    LaunchedEffect(key1 = true) {
        movieDetailViewModel.getMovieCart(userName)
        movieDetailViewModel.getFavorites()
        tMovieName.value = recievedMovide.name
        tMovieImg.value = recievedMovide.image
        tMoviePrice.value = recievedMovide.price
        tMovieCategory.value = recievedMovide.category
        tMovieRating.value = recievedMovide.rating
        tMovieYear.value = recievedMovide.year
        tMovieDirector.value = recievedMovide.director
        tMovieDescription.value = recievedMovide.description
        for (i in 0..favoritesList.value.size-1) {
            moviesInFavorites.add(favoritesList.value[i].name)
        }
        if (moviesInFavorites.contains(recievedMovide.name)) {
            isFavIconClicked.value = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("homepage")
                        },
                        colors = IconButtonColors(
                            contentColor = ButtonColor,
                            containerColor = MainColor,
                            disabledContentColor = TextColor,
                            disabledContainerColor = TextColor
                        )
                    ) {
                        Icon(painterResource(R.drawable.go_back_icon),"")
                    }
                },
                title = {
                    Text("")
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
        /*bottomBar = {
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
        }*/
        containerColor = MainColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.size((screenWidth).dp, 375.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageUrl = "http://kasimadalan.pe.hu/movies/images/${tMovieImg.value}"
                GlideImage(
                    imageModel = imageUrl,
                    modifier = Modifier.size(250.dp,375.dp).clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                )
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = (screenHeight/15).dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(isFavIconClicked.value == false) {
                        IconButton(
                            onClick = {
                                for (i in 0..favoritesList.value.size-1) {
                                    moviesInFavorites.add(favoritesList.value[i].name)
                                }
                                isFavIconClicked.value = true
                                if(!moviesInFavorites.contains(recievedMovide.name)) {
                                    movieDetailViewModel.addToFvorites(recievedMovide.name, recievedMovide.image, recievedMovide.category, recievedMovide.year, recievedMovide.director, recievedMovide.description)
                                }
                                //moviesInFavorites.add(recievedMovide.name)
                            },
                            colors = IconButtonColors(
                                contentColor = TextColor,
                                containerColor = MainColor,
                                disabledContentColor = TextColor,
                                disabledContainerColor = TextColor
                            ),
                        ) {
                            Icon(painterResource(R.drawable.add_favourites_icon),"", modifier = Modifier.size((screenWidth/12).dp, (screenHeight/20).dp))
                        }
                    } else {
                        IconButton(
                            onClick = {
                                for (i in 0..favoritesList.value.size-1) {
                                    moviesInFavorites.add(favoritesList.value[i].name)
                                }
                            },
                            colors = IconButtonColors(
                                contentColor = TextColor,
                                containerColor = MainColor,
                                disabledContentColor = TextColor,
                                disabledContainerColor = TextColor
                            ),
                        ) {
                            Icon(painterResource(R.drawable.star_filled),"", modifier = Modifier.size((screenWidth/12).dp, (screenHeight/20).dp))
                        }
                    }
                    /*IconButton(
                        onClick = {
                            for (i in 0..favoritesList.value.size-1) {
                                moviesInFavorites.add(favoritesList.value[i].name)
                            }
                            if(!moviesInFavorites.contains(recievedMovide.name)) {
                                movieDetailViewModel.addToFvorites(recievedMovide.name, recievedMovide.image, recievedMovide.category, recievedMovide.year, recievedMovide.director, recievedMovide.description)
                            }
                        },
                        colors = IconButtonColors(
                            contentColor = TextColor,
                            containerColor = MainColor,
                            disabledContentColor = TextColor,
                            disabledContainerColor = TextColor
                        ),
                    ) {
                        Icon(painterResource(R.drawable.star_filled),"", modifier = Modifier.size((screenWidth/12).dp, (screenHeight/20).dp))
                    }*/
                    /*Box(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/20).dp).width((screenWidth/5).dp).background(color = InfoBoxColor)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.year_icon),"", modifier = Modifier.size(16.dp,16.dp), TextColor)
                            Text("${recievedMovide.year}", color = TextColor, fontSize = 14.sp)
                        }
                    }*/
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/20).dp).width((screenWidth/5).dp).background(color = InfoBoxColor)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.star_icon),"", modifier = Modifier.size(14.dp,14.dp), Color.Yellow)
                            Text("${recievedMovide.rating}", color = TextColor, fontSize = 14.sp)
                        }
                    }
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/14).dp).width((screenWidth/5).dp).background(color = InfoBoxColor)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("₺${recievedMovide.price}", color = TextColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp, end = 5.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(

                ) {
                    Icon(painterResource(R.drawable.movie_camera_icon),"", modifier = Modifier.size((screenWidth/10).dp, (screenHeight/17).dp).padding(end = 8.dp), TextColor)
                    Column {
                        Text(tMovieName.value, color = TextColor, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        Row {
                            Text(tMovieCategory.value, color = TextColor, fontSize = 12.sp)
                            Text(" , ${tMovieYear.value}", color = TextColor, fontSize = 12.sp)
                        }
                    }
                }
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/22).dp).width((screenWidth/2.3).dp).background(color = InfoBoxColor)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.director_icon),"", modifier = Modifier.size(16.dp,16.dp), TextColor)
                        Text(text = recievedMovide.director, color = TextColor, fontSize = 13.sp)
                    }
                }
            }
            Box(
                modifier = Modifier.height((screenHeight/8).dp).width((screenWidth).dp).background(color = MainColor),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = recievedMovide.description, color = TextColor, fontSize = 13.sp)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₺${totalPrice.value}", color = TextColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    var orderAmountInt = orderAmount.value
                    if (orderAmountInt > 1) {
                        orderAmountInt = orderAmountInt - 1
                        orderAmount.value = orderAmountInt
                        totalPrice.value = totalPrice.value  - recievedMovide.price
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
                        Text(orderAmount.value.toString(), color = TextColor)
                    },
                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).height((screenHeight/18).dp).width((screenWidth/5).dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = InfoBoxColor
                    )
                )
                IconButton(onClick = {
                    var orderAmountInt = orderAmount.value
                    orderAmountInt = orderAmountInt + 1
                    orderAmount.value = orderAmountInt
                    totalPrice.value = totalPrice.value + recievedMovide.price
                }) {
                    Icon(painterResource(R.drawable.add_one_icon),"")
                }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(bottom = 1.dp)
            ) {
                Button(
                    modifier = Modifier.height((screenHeight/15).dp).width((screenWidth/1.7).dp).padding(top = 5.dp, bottom = 2.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor,
                        contentColor = TextColor
                    ),
                    onClick = {
                        try {
                            for (i in 0..movieCartList.value.size-1) {
                                moviesInCart.add(movieCartList.value[i].name)
                            }

                            if (moviesInCart.contains(recievedMovide.name)) {
                                for (i in 0..size-1) {
                                    if (movieCartList.value[i].name == recievedMovide.name) {
                                        movieDetailViewModel.deleteMovieCart(movieCartList.value[i].cartId, userName)
                                        movieDetailViewModel.addMovieToCart(recievedMovide.name, recievedMovide.image, recievedMovide.price, recievedMovide.category, recievedMovide.rating, recievedMovide.year, recievedMovide.director, recievedMovide.description, orderAmount.value + movieCartList.value[i].orderAmount, userName)
                                    }
                                }
                            } else {
                                movieDetailViewModel.addMovieToCart(
                                    recievedMovide.name,
                                    recievedMovide.image,
                                    recievedMovide.price,
                                    recievedMovide.category,
                                    recievedMovide.rating,
                                    recievedMovide.year,
                                    recievedMovide.director,
                                    recievedMovide.description,
                                    orderAmount.value,
                                    userName
                                )
                            }
                        }catch (e:Exception){

                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.add_to_cart_icon),"")
                        Text(stringResource(R.string.add_to_cart), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

/*Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(tMovieName.value)
                Text(tMovieDirector.value)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₺${tMoviePrice.value}")
                Text("${tMovieRating.value}/10")
            }
            Text("${tMovieYear.value}")
            Text(tMovieCategory.value)
            Text(tMovieDescription.value)*/
/*Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 5.dp),
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
       try {
            for (i in 0..movieCartList.value.size-1) {
                moviesInCart.add(movieCartList.value[i].name)
            }

            if (moviesInCart.contains(recievedMovide.name)) {
                for (i in 0..size-1) {
                    if (movieCartList.value[i].name == recievedMovide.name) {
                        movieDetailViewModel.deleteMovieCart(movieCartList.value[i].cartId, userName)
                        movieDetailViewModel.addMovieToCart(recievedMovide.name, recievedMovide.image, recievedMovide.price, recievedMovide.category, recievedMovide.rating, recievedMovide.year, recievedMovide.director, recievedMovide.description, orderAmount.value + movieCartList.value[i].orderAmount, userName)
                    }
                }
            } else {
                movieDetailViewModel.addMovieToCart(
                    recievedMovide.name,
                    recievedMovide.image,
                    recievedMovide.price,
                    recievedMovide.category,
                    recievedMovide.rating,
                    recievedMovide.year,
                    recievedMovide.director,
                    recievedMovide.description,
                    orderAmount.value,
                    userName
                )
            }
        }catch (e:Exception){

        }
    }
) {
    Text("Add To Cart")
}*/