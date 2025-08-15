package com.example.mtaaconnect.ui.theme.screens.client

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mtaaconnect.data.ClientViewModel
import com.example.mtaaconnect.models.Client
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

@Composable
fun updateClientScreen(
    navController: NavController,
    clientId: String
) {
    val clientViewModel: ClientViewModel = viewModel()
    var client by remember { mutableStateOf<Client?>(null) }

    val context = LocalContext.current


    LaunchedEffect(clientId) {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("Patients").child(clientId)
            val snapshot = ref.get().await()
            client = snapshot.getValue(Client::class.java)?.apply {
                id = clientId
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (client == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }


    var name by remember { mutableStateOf(client?.name ?: "") }
    var gender by remember { mutableStateOf(client?.gender ?: "") }
    var nationality by remember { mutableStateOf(client?.nationality ?: "") }
    var phonenumber by remember { mutableStateOf(client?.phonenumber ?: "") }
    var age by remember { mutableStateOf(client?.age ?: "") }
    var diagnosis by remember { mutableStateOf(client?.diagnosis ?: "") }
    var nextOfKin by remember { mutableStateOf(client?.nextOfKin ?: "") }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri -> imageUri.value = uri }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFF3E0), Color(0xFFFFCCBC))
                )
            )
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Update Patient",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF57C00)
                )

                Spacer(modifier = Modifier.height(16.dp))


                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier
                        .size(140.dp)
                        .clickable { launcher.launch("image/*") }
                        .shadow(8.dp, CircleShape)
                ) {
                    AnimatedContent(
                        targetState = imageUri.value ?: client?.imageUrl,
                        label = "Image Picker Animation"
                    ) { targetImage ->
                        AsyncImage(
                            model = targetImage,
                            contentDescription = "Patient Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Text(
                    text = "Tap to change picture",
                    fontSize = 14.sp,
                    color = Color(0xFF6D4C41),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Divider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = Color(0xFFFFB74D),
                    thickness = 1.dp
                )

                val fieldModifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)

                val fieldShape = RoundedCornerShape(14.dp)


                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = fieldModifier,
                    shape = fieldShape
                )

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") },
                    modifier = fieldModifier,
                    shape = fieldShape
                )

                OutlinedTextField(
                    value = nationality,
                    onValueChange = { nationality = it },
                    label = { Text("Nationality") },
                    modifier = fieldModifier,
                    shape = fieldShape
                )

                OutlinedTextField(
                    value = phonenumber,
                    onValueChange = { phonenumber = it },
                    label = { Text("Phone Number") },
                    modifier = fieldModifier,
                    shape = fieldShape
                )

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = fieldModifier,
                    shape = fieldShape
                )

                OutlinedTextField(
                    value = diagnosis,
                    onValueChange = { diagnosis = it },
                    label = { Text("Diagnosis") },
                    modifier = fieldModifier.height(120.dp),
                    shape = fieldShape,
                    maxLines = 5
                )

                OutlinedTextField(
                    value = nextOfKin,
                    onValueChange = { nextOfKin = it },
                    label = { Text("Next of Kin") },
                    modifier = fieldModifier,
                    shape = fieldShape
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text("Go Back", color = Color.DarkGray)
                    }

                    Button(
                        onClick = {
                            clientViewModel.updateClient(
                                clientId,
                                imageUri.value,
                                name,
                                gender,
                                nationality,
                                phonenumber,
                                age,
                                diagnosis,
                                nextOfKin,
                                context,
                                navController
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text("Update", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
