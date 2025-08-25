package com.example.mtaaconnect.models

import java.util.UUID



data class Post(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

