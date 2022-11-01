package com.example.twittertest.presentation.screens.addPost

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lib_data.domain.models.PostAdd
import com.example.lib_data.util.Resource
import com.example.twittertest.ui.theme.TwitterTestTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddPost(
    navController: NavController
) {
    val message = remember { mutableStateOf("Add Post") }

    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }
    CustomPost(message, openDialog, editMessage, navController)

    Box(modifier = Modifier.wrapContentSize()) {
        Scaffold(
            modifier = Modifier.wrapContentSize(),
            backgroundColor = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message.value,
                    fontSize = 24.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        editMessage.value = message.value
                        openDialog.value = true
                    }
                ) {
                    Text("Create Post")
                }
            }
        }

        if (openDialog.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = contentColorFor(MaterialTheme.colors.background)
                            .copy(alpha = 0.6f)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            openDialog.value = false
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                CustomPost(message, openDialog, editMessage, navController)
            }
        }
    }
}

@Composable
fun CustomPost(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>,
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    val newPost = viewModel.createPost.collectAsState().value
    LaunchedEffect(key1 = newPost) {
        when (newPost) {
            is Resource.Error -> println("CustomPost Error!")
            Resource.Loading -> println("CustomPost Loading...")
            is Resource.Success -> navController.navigate("dashboard_screen")
        }
    }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "Input Message")

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = editMessage.value,
                onValueChange = { editMessage.value = it },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            Button(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    message.value = editMessage.value
                    openDialog.value = false
                    println(editMessage.value)
                    viewModel.createPost(
                        post = PostAdd(
                            username = "joejoe",
                            content = editMessage.value
                        )
                    )
                }
            ) {
                Text("OK")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview13() {
    TwitterTestTheme {
        AddPost(navController = rememberNavController())
    }
}
