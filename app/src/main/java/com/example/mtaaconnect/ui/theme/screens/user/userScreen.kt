package com.example.mtaaconnect.ui.theme.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mtaaconnect.R
import com.example.mtaaconnect.navigation.ROUTE_LOGIN

@Composable
fun userScreen(navController: NavController) {

    Scaffold(
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Background image filling the screen
            Image(
                painter = painterResource(id = R.drawable.mtaa2),
                contentDescription = "register wallpaper",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            // Overlayed content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Continue As",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 40.dp)
                )


                Button(
                    onClick = { navController.navigate(ROUTE_LOGIN) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC5500)), // light purple
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Service provider", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }

                // Doc Button (dark purple)
                Button(
                    onClick = { navController.navigate("ROUTE_DOC_LOGIN") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray), // dark purple
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Recipient", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserScreenPreview() {
    userScreen(rememberNavController())
}