package com.example.twittertest.presentation.screens.view_post

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lib_data.domain.models.Post
import com.example.lib_data.util.Constants
import com.example.lib_data.util.Resource
import com.example.lib_data.util.ScreenConstants
import com.example.twittertest.ui.theme.TwitterTestTheme


@Composable
fun PostDetail(
    id: Int? = null,
//    dataStore: DataStorePrefSource,
    viewModel: ViewPostViewModel = hiltViewModel(),
    navController: NavController
) {
    viewModel.postDetail(id)
    val post by viewModel.post.collectAsState()
    when (post) {
        is Resource.Error -> println("Post Detail Error!")
        Resource.Loading -> println("Post Detail Loading...")
        is Resource.Success -> {
            val postD = (post as Resource.Success<Post>).data
            Log.d("POST LOGGER", " POST RIGHT HERE ${postD}")
            singlePost(data = postD, navController)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
//                    .clickable {
////                println(" Post has been clicked")
//                    }

            ) {
                Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max))
                {
                    Image(
                        painter = rememberAsyncImagePainter(Constants.IMAGE),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Column() {
                        Text(text = "@${postD.username}")
                        Text(
                            text = postD.content,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                        Text(
                            text = "Comment here",
                            fontSize = 8.sp
                        )
                    }
                }
                Text(text = postD.createdAt, textAlign = TextAlign.End, fontSize = 5.sp)
            }
        }
        else ->
            println(" Error in getting post")
    }
//    commentButton()
}


@Composable
fun singlePost(data: Post, navController: NavController) {
//    val message = remember { mutableStateOf("Add Post") }
//
//    val openDialog = remember { mutableStateOf(false) }
//    val editMessage = remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable {
//                println(" Post has been clicked")
            }

    ) {
        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max))
        {
            Image(
                painter = rememberAsyncImagePainter(
                    "https://image.shutterstock.com/image-vector/one-piece-character-luffy-nika-600w-2210725005.jpg"
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column() {
                Text(text = "@${data.username}")
                Text(
                    text = data.content,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                TextButton(onClick = {
                    println("add comment Button clicked")
                    navController.navigate(ScreenConstants.addCommentScreen)


                }) {
                    Text("add comment", fontSize = 10.sp)
                }
//                commentButton()
            }
        }
        Text(text = data.createdAt, textAlign = TextAlign.End, fontSize = 5.sp)
    }
}

@Composable
fun commentButton() {
    Button(
        onClick = {
            println(" Add comment Button clicked")
        }, modifier = Modifier
            .wrapContentSize()
            .size(30.dp, 20.dp)
//            .height(15.dp)
    ) {
        Text(text = " commment")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview10() {
    TwitterTestTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .clickable {
//                println(" Post has been clicked")
                }

        ) {
            Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max))
            {
                Image(
                    painter = rememberAsyncImagePainter(
                        "https://image.shutterstock.com/image-vector/one-piece-character-luffy-nika-600w-2210725005.jpg"
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Column() {
                    Text(text = "@$")
                    Text(
                        text = "data.content",
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Text(
                        text = "Comment",
                        fontSize = 8.sp
                    )
                }
            }
            Text(text = "data.createdAt", textAlign = TextAlign.End, fontSize = 5.sp)
        }
        commentButton()
    }
}
