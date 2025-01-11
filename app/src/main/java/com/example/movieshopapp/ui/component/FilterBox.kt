package com.example.movieshopapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieshopapp.ui.theme.InfoBoxColor
import com.example.movieshopapp.ui.theme.InfoBoxColorDark
import com.example.movieshopapp.ui.theme.MainColorDark
import com.example.movieshopapp.ui.theme.TextColor
import com.example.movieshopapp.ui.theme.TextColorDark

//filter box component in homepage top
@Composable
fun FilterBox(width:Int, text:String, darkTheme:Boolean) {
    Button(
        onClick = {},
        modifier = Modifier.height(50.dp).width(width.dp).padding(start = 5.dp, end = 5.dp).shadow(elevation = 5.dp, spotColor = MainColorDark, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(darkTheme) InfoBoxColorDark else InfoBoxColor,
            contentColor = if(darkTheme) TextColorDark else TextColor //dark theme color check for colors
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 13.sp)
        }
    }
}