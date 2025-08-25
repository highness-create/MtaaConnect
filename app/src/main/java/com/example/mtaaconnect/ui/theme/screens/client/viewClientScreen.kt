package com.example.mtaaconnect.ui.theme.screens.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mtaaconnect.data.ClientViewModel
import com.example.mtaaconnect.models.Client

@Composable
fun ClientListScreen(navController: NavController) {
    val clientViewModel: ClientViewModel = viewModel()
    val clients = clientViewModel.clients
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        clientViewModel.fetchClient(context)
    }

    LazyColumn {
        items(clients) { client ->
            ClientCard(
                client = client,
                onDelete = { clientId -> clientViewModel.deleteClient(clientId, context) },
                navController = navController
            )
        }
    }
}

@Composable
fun ClientCard(
    client: Client,
    onDelete: (String) -> Unit,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this client?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    client.id?.let { onDelete(it) }
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF57C00)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!client.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = client.imageUrl,
                        contentDescription = "Client Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFB74D)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = client.name?.take(2)?.uppercase() ?: "NA",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = client.name ?: "Unnamed",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Age: ${client.age}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF424242)
                    )
                    Text(
                        text = "Diagnosis: ${client.diagnosis}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF616161)
                    )

                    Text(
                        text = "Next of Kin: ${client.nextOfKin ?: "N/A"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF37474F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("update_patient/${client.id}")
                    }
                ) {
                    Text("Update", color = Color(0xFFFFE0B2))
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(
                    onClick = { showDialog = true }
                ) {
                    Text("Delete", color = Color(0xFFD32F2F))
                }
            }
        }
    }
}


