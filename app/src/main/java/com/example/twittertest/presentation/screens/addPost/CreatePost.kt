package com.example.twittertest.presentation.screens.addPost


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lib_data.domain.models.PostAdd
import com.example.twittertest.R
import com.example.twittertest.ui.theme.TwitterTestTheme

@Composable
fun CreatePost(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painterResource(R.drawable.twitter_logo),
            contentDescription = "logo"
        )
        var username by remember {
            mutableStateOf("")
        }
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text(text = " @username") },
        )
        //
        var content by remember {
            mutableStateOf("")
        }
        TextField(
            value = content,
            modifier = Modifier
                .width(280.dp)
                .height(400.dp),
            onValueChange = { content = it },
            placeholder = { Text(text = "Content") },
        )
        //
        val context = LocalContext.current
        Button(onClick = {
            if (username != "" && content != "") {
                viewModel.createPost(post = PostAdd(username = username, content = content))
                navController.navigate("dashboard_screen")
            } else {
                Toast.makeText(context, "Cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }) {
            Text("Add Post")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview15() {
    TwitterTestTheme {
        CreatePost(navController = rememberNavController())
    }
}
