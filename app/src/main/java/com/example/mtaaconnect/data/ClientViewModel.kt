package com.example.mtaaconnect.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mtaaconnect.models.Client
import com.example.mtaaconnect.navigation.ROUTE_DASHBOARD
import com.example.mtaaconnect.navigation.ROUTE_CLIENT_LIST
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class ClientViewModel:ViewModel() {
    val cloudinaryUrl = "https://api.cloudinary.com/v1_1/di2iwtvly/image/upload"
    val uploadPreset = "MtaaConnecT"
    fun uploadClient(imageUri: Uri?,name:String,gender:String,nationality:String,phonenumber:String, age:String,diagnosis:String,context: Context, navController: NavController){

        viewModelScope.launch (Dispatchers.IO){
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context,it) }
                val ref = FirebaseDatabase.getInstance().getReference("Clients").push()
                val clientData = mapOf(
                    "id" to ref.key,
                    "name" to name,
                    "gender" to gender,
                    "nationality" to nationality,
                    "phonenumber" to phonenumber,
                    "age" to age,
                    "diagnosis" to diagnosis,
                    "imageUrl" to imageUrl
                )
                ref.setValue(clientData).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Client saved Successfully",Toast.LENGTH_LONG).show()
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Client not saved",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadToCloudinary(context:Context,uri: Uri):String{
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw  Exception("Image read failed")
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file","image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(),fileBytes))
            .addFormDataPart("upload_preset",uploadPreset).build()
        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if(!response.isSuccessful) throw Exception("Upload failed")
        val responseBody = response.body?.string()
        val secureUrl = Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to get image URL")
    }
    private val _clients = mutableStateListOf<Client>()
    val clients: List<Client> = _clients

    fun fetchClients(context: Context){
        val ref = FirebaseDatabase.getInstance().getReference("Clients")
        ref.get().addOnSuccessListener { snapshot ->
            _clients.clear()
            for(child in snapshot.children){
                val patient = child.getValue(Client::class.java)
                patient?.let {_clients.add(it)}
            }
        }.addOnFailureListener {
            Toast.makeText(context,"Failed to load clients", Toast.LENGTH_LONG).show()
        }
    }
    fun  deleteClient(clientsId: String,context: Context){
        val ref = FirebaseDatabase.getInstance()
            .getReference("Clients").child(clientsId)
        ref.removeValue().addOnSuccessListener {
            _clients.removeAll{it.id == clientsId}
        }.addOnFailureListener {
            Toast.makeText(context,"Client not deleted", Toast.LENGTH_LONG).show()
        }
    }
    fun updateClient(
        clientsId: String,
        imageUri: Uri?,
        name: String,
        gender: String,
        nationality: String,
        phonenumber: String,
        age: String,
        diagnosis: String,
        nextOfKin: String,
        context: Context,
        navController: NavController
    ){

        viewModelScope.launch (Dispatchers.IO){
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) }
                val updatePatient = mapOf(
                    "id" to clientsId,
                    "name" to name,
                    "gender" to gender,
                    "nationality" to nationality,
                    "phonenumber" to phonenumber,
                    "age" to age,
                    "diagnosis" to diagnosis,
                    "nextOfKin" to nextOfKin,
                    "imageUrl" to imageUrl
                )
                val ref = FirebaseDatabase.getInstance()
                    .getReference("Patients").child(clientsId)
                ref.setValue(updatePatient).await()
                fetchClients(context)
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Client updated successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)

                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Update Failed",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun fetchClient(context: Context) {

    }
}