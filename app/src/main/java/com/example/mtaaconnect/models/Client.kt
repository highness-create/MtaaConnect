package com.example.mtaaconnect.models

data class Client(

    var id: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val nationality: String? = null,
    val phonenumber: String? = null,
    val age: String? = null,
    val diagnosis: String? = null,
    val imageUrl: String? = null,
    var nextOfKin: String? = null
)