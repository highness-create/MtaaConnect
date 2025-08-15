package com.example.mtaaconnect.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mtaaconnect.models.UserModel
import com.example.mtaaconnect.navigation.ROUTE_DASHBOARD
import com.example.mtaaconnect.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel:ViewModel() {
    private val auth: FirebaseAuth=FirebaseAuth.getInstance()
    fun signup(
        username: String,
        email: String,
        password: String,
        confirmpassword :String,
        navController: NavController,
        context: Context
    ) {
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().getReference("Users")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val user = mapOf(
                        "uid" to userId,
                        "username" to username,
                        "email" to email
                    )
                    database.child(userId).setValue(user).addOnSuccessListener {
                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show()
                        navController.navigate(ROUTE_LOGIN)
                    }
                } else {
                    Toast.makeText(context, task.exception?.message ?: "Error", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUserToDatabase(user:UserModel,navController: NavController,context: Context){
        val dbRef = FirebaseDatabase.getInstance().getReference("User/${user.userId}")
        dbRef.setValue(user).addOnCompleteListener{
                task ->
            if (task.isSuccessful){
                Toast.makeText(context,"User Registered successfully",
                    Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN){
                    popUpTo(0)
                }
            }else{
                Toast.makeText(context,task.exception?.message ?: "Failed to save user",
                    Toast.LENGTH_LONG).show()
            }
        }


    }
    fun login(email: String,password: String,navController: NavController,context: Context){
        if (email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Email and Password required",Toast.LENGTH_LONG).show()
            return
        }
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                task ->
            if (task.isSuccessful){
                Toast.makeText(context,"Login Successful",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_DASHBOARD){
                    popUpTo(0)
                }
            }else{
                Toast.makeText(context,task.exception?.message ?: "Login failed",
                    Toast.LENGTH_LONG).show()
            }}}
}
