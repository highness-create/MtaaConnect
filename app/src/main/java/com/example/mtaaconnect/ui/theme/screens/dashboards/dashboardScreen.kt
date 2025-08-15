package com.example.mtaaconnect.ui.theme.screens.dashboards

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mtaaconnect.R
import com.example.mtaaconnect.navigation.ROUTE_ADD_CLIENT
import com.example.mtaaconnect.navigation.ROUTE_CLIENT_LIST
import com.example.mtaaconnect.navigation.ROUTE_DASHBOARD
import com.example.mtaaconnect.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dashboardScreen(navController: NavController) {
    val context = LocalContext.current
    var showAlertDialog by remember { mutableStateOf(false) }


    val patientCount by remember { mutableStateOf((100..120).random()) }
    val criticalAlerts by remember { mutableStateOf(listOf("Patient John Doe needs attention", "Low oxygen level detected")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Advanced Dashboard",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2)),
                actions = {
                    IconButton(onClick = { showAlertDialog = true }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {


            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Real-time Patient Count", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Currently Active Patients: $patientCount", fontSize = 16.sp, color = Color.DarkGray)
                }
            }


            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Patient Admission Trend", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Chart Placeholder")
                    }
                }
            }


            Text("Quick Actions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { navController.navigate(ROUTE_ADD_CLIENT) }, modifier = Modifier.weight(1f)) {
                    Text("Add Client")
                }
                Button(onClick = { navController.navigate(ROUTE_CLIENT_LIST) }, modifier = Modifier.weight(1f)) {
                    Text("View Client")
                }
            }
        }


        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = { Text("Critical Alerts") },
                text = {
                    Column {
                        criticalAlerts.forEach { alert ->
                            Text("• $alert", fontSize = 14.sp)
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAlertDialog = false }) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}
