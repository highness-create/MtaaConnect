package com.example.mtaaconnect.ui.theme.screens.home

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mtaaconnect.data.PostViewModel
import java.text.DateFormat
import java.util.*

@Composable
fun homeScreen(navController: NavController, postViewModel: PostViewModel) {
    val posts = postViewModel.posts
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "📢 Recent Posts",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (posts.isEmpty()) {
            Text("No posts yet. Be the first to post!")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                post.content,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = DateFormat.getDateTimeInstance().format(Date(post.timestamp)),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // 🔁 Repost button
                                Button(
                                    onClick = {
                                        postViewModel.addPost(post.content)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                                ) {
                                    Text("Repost", color = Color.White)
                                }

                                // 📤 Share button
                                Button(
                                    onClick = {
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, post.content)
                                            type = "text/plain"
                                        }
                                        context.startActivity(
                                            Intent.createChooser(shareIntent, "Share via")
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Text("Share", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun homeScreenPreview() {
    val fakeNavController = rememberNavController()
    val fakeViewModel = remember { PostViewModel() }

    // Mock posts for preview
    LaunchedEffect(Unit) {
        if (fakeViewModel.posts.isEmpty()) {
            fakeViewModel.addPost("🚀 Launching our new platform today!")
            fakeViewModel.addPost("📣 Don't forget the community meeting at 4 PM.")
        }
    }

    homeScreen(navController = fakeNavController, postViewModel = fakeViewModel)
}
