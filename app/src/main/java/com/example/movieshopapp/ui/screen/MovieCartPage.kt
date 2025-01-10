package com.example.movieshopapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.clearCompositionErrors
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.movieshopapp.R
import com.example.movieshopapp.data.entity.MovieCart
import com.example.movieshopapp.ui.theme.ButtonColor
import com.example.movieshopapp.ui.theme.InfoBoxColor
import com.example.movieshopapp.ui.theme.MainColor
import com.example.movieshopapp.ui.theme.TextColor
import com.example.movieshopapp.ui.viewmodel.MovieCartViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import kotlin.math.truncate
import androidx.compose.foundation.layout.Column as Column1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCartPage(navController: NavController, movieCartViewModel: MovieCartViewModel) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    var movieCartList = movieCartViewModel.movieCartList.observeAsState(listOf())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val userName = "fatih_yanik"
    val movieCartHasError = movieCartViewModel.hasError.observeAsState()
    val totalCartPrice = remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        movieCartViewModel.getMovieCart(userName)
        for (i in 0..movieCartList.value.size-1) {
            totalCartPrice.value = totalCartPrice.value + (movieCartList.value[i].price * movieCartList.value[i].orderAmount)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
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
                title = { //sonradan arama kısmı yapılabilir
                    Text("My Cart")
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
        containerColor = MainColor,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
            if (movieCartHasError.value == false) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        count = movieCartList.value.count(),
                        itemContent = {
                            var movieCart = movieCartList.value[it]
                            val imageUrl = "http://kasimadalan.pe.hu/movies/images/${movieCart.image}"

                            Card(
                                modifier = Modifier.padding(all = 3.dp),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize().background(color = InfoBoxColor),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    GlideImage(
                                        imageModel = imageUrl,
                                        modifier = Modifier.size(100.dp, 150.dp).padding(5.dp).clip(shape = RoundedCornerShape(15.dp)),
                                    )
                                    Column(
                                        modifier = Modifier.padding(start = 10.dp)
                                    ) {
                                        Text(text = movieCart.name, color = TextColor, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                                        Text(text = " ₺${movieCart.price} x ${movieCart.orderAmount}", color = TextColor, fontSize = 15.sp)
                                    }
                                    Column(
                                        verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.End,
                                        modifier = Modifier.fillMaxSize().padding(10.dp)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    val snackbar = snackbarHostState.showSnackbar(
                                                        message = "${movieCart.name} silinsin mi?",
                                                        actionLabel = "YES"
                                                    )
                                                    if (snackbar == SnackbarResult.ActionPerformed) {
                                                        movieCartViewModel.deleteMovieCart(movieCart.cartId, userName)
                                                        if (totalCartPrice.value > 0 ) {
                                                            totalCartPrice.value = totalCartPrice.value - (movieCart.price*movieCart.orderAmount)
                                                        }
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(painterResource(R.drawable.delete_icon), "", modifier = Modifier.size(24.dp,24.dp) ,ButtonColor)
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            } else if (movieCartHasError.value == true){
                //movieCartViewModel.getMovieCart(userName)
                totalCartPrice.value = 0
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Empty cart", color = TextColor)
                }
            }
            Box(
                modifier = Modifier.offset((screenWidth/11).dp, (screenHeight/1.15).dp).size((screenWidth/1.2).dp,50.dp).clip(
                    RoundedCornerShape(20.dp)
                ).background(color = ButtonColor)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Cart total:", color = TextColor, modifier = Modifier.padding(start = 5.dp))
                    Text(" ₺${totalCartPrice.value}", color = TextColor)
                    Button(
                        modifier = Modifier.height(50.dp).width((screenWidth/2.2).dp).padding(all = 5.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = InfoBoxColor,
                            contentColor = ButtonColor
                        ),
                        onClick = {}
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Confirm Cart", fontSize = 18.sp)
                        }
                    }
                }
            }
    }
}