package com.example.mtaaconnect.ui.theme.screens.services

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun viewservicesScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") } // placeholder for image URL

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Service Provider Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Input Fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL (optional)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Short Description") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = skills,
            onValueChange = { skills = it },
            label = { Text("Skills (e.g., HTML, CSS, Django)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        NavigationBarItem(
            selected = selectedItem.value == 1,
            onClick = {
                selectedItem.value = 1
                if (!isInPreview) {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:0700000000")
                    }
                    context.startActivity(intent)
                }
            },
            icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
            label = { Text("Phone") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Preview Section
        Text(
            text = "Preview",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (imageUrl.isNotBlank()) {
                    // Use placeholder until real image loading is implemented
                    Text(text = "[Image Placeholder]", color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                }

                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
                Text(text = "Skills: $skills", fontSize = 14.sp, color = Color.Black)
                Text(text = "Phone: $phone", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewServicesScreenPreview() {
    viewservicesScreen(navController = rememberNavController())
}
