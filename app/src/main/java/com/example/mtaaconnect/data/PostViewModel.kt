package com.example.mtaaconnect.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mtaaconnect.models.Post

class PostViewModel : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> = _posts

    fun addPost(content: String) {
        _posts.add(0, Post(content = content)) // adds newest on top
    }
}

