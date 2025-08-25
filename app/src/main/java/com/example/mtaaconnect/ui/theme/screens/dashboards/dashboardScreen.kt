package com.example.mtaaconnect.ui.theme.screens.dashboards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mtaaconnect.data.PostViewModel
import com.example.mtaaconnect.navigation.ROUTE_ADD_CLIENT
import com.example.mtaaconnect.navigation.ROUTE_CLIENT_LIST
import com.example.mtaaconnect.navigation.ROUTE_HOME
//import com.example.mtaaconnect.navigation.ROUTE_HOME
import com.example.mtaaconnect.navigation.ROUTE_POST
import com.example.mtaaconnect.navigation.ROUTE_SERVICES
//import com.example.mtaaconnect.navigation.ROUTE_PAYMENTS

// Updated Data model with route
data class DashboardItem(val title: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dashboardScreen(navController: NavController, ) {
    val context = LocalContext.current
    var showAlertDialog by remember { mutableStateOf(false) }

    val clientCount by remember { mutableStateOf((100..120).random()) }
    val criticalAlerts by remember { mutableStateOf(listOf("Client Mary Jane needs assistance", "Inquiries on last import")) }

    var searchQuery by remember { mutableStateOf("") }
    val postViewModel:PostViewModel = viewModel()
    val dashboardItems = listOf(
        DashboardItem("Home", Icons.Default.Home, route = ROUTE_HOME),
        DashboardItem("Post", Icons.Default.Edit, route = ROUTE_POST),
        DashboardItem("View Services", Icons.Default.List, route = ROUTE_SERVICES),
//        DashboardItem("Make Payments", Icons.Default.ThumbUp, route = ROUTE_PAYMENTS)
    )

    val filteredItems = dashboardItems.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MtaaConnecT", fontSize = 22.sp, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF9800)),
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

            // Real-time client card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Real-time Client Count", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Currently Active Clients: $clientCount", fontSize = 16.sp, color = Color.DarkGray)
                }
            }


            // Quick Actions
            Text("Quick Actions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate(ROUTE_ADD_CLIENT) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add Client")
                }
                Button(
                    onClick = { navController.navigate(ROUTE_CLIENT_LIST) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("View Client")
                }
            }

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                placeholder = { Text("Search dashboard...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(50.dp)
            )

            // Filtered Dashboard Items
            if (filteredItems.isEmpty()) {
                Text("No results found", style = MaterialTheme.typography.bodyMedium)
            } else {
                Column {
                    filteredItems.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rowItems.forEach { item ->
                                DashboardCard(title = item.title, icon = item.icon) {
                                    navController.navigate(item.route)
                                }
                            }
                            if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
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

@Composable
fun DashboardCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    dashboardScreen(navController = navController)
}
