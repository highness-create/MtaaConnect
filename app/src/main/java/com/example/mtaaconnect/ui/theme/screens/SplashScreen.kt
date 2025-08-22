package com.example.mtaaconnect.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mtaaconnect.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToNext: () -> Unit) {
    val splashScreenDuration = 3000L


    LaunchedEffect(Unit) {
        delay(splashScreenDuration)
        onNavigateToNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCC5500)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.mtaa),
                contentDescription = "App logo",
                contentScale = ContentScale.Crop, // Optional, depending on your image shape
                modifier = Modifier
                    .size(220.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)) // Optional: makes corners rounded
                    .background(Color.White)         // Optional: gives background if image has transparency
                    .border(2.dp, Color(0xFFCC5500), RoundedCornerShape(16.dp)) // Burnt orange border
                    .align(Alignment.CenterHorizontally) // Align in parent if needed
            )


            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "MtaaConnecT",
                color = Color(0xFFCC5500),
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Post Share Tujengane",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}