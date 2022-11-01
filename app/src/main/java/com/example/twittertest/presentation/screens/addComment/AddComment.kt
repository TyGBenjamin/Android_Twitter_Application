package com.example.twittertest.presentation.screens.addComment

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
import androidx.compose.runtime.MutableState
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
import com.example.lib_data.domain.models.Comment
import com.example.lib_data.util.DataStorePrefSource
import com.example.twittertest.ui.theme.TwitterTestTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommentCompose(
    navController: NavController
) {
    val message = remember { mutableStateOf("Add Post") }

    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }
    CustomComment(message, openDialog, editMessage, navController)

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
                CustomComment(message, openDialog, editMessage, navController)
            }
        }
    }
}

@Composable
fun CustomComment(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>,
    navController: NavController,
    dataStore: DataStorePrefSource,
    viewModel: CommentViewModel = hiltViewModel()
) {
    val commentData = CommentData.createBlankInstance()


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
                value = commentData.message,
                onValueChange = { commentData.message = it },
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

            AddCommentButton(
                commentData = commentData,
                navigate = { navController.navigate(it) }
            ) {
                openDialog.value = false
                println(it.content)
                viewModel.createComment(
                    comment = it
                )
            }

        }
    }
}

data class CommentData(
    var message: String = "",
    val username: String = "",
    val commentID: Int = 0,
    val postID: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = ""
) {
    companion object {
        fun createBlankInstance(): CommentData {
            return CommentData()
        }
    }
}

@Composable
fun AddCommentButton(
    commentData: CommentData,
    navigate: (navigateTo: String) -> Unit,
    onCommentAdded: (newComment: Comment) -> Unit
) {
    Button(
        onClick = {
            onCommentAdded(
                Comment(
                    username = commentData.username,
                    content = commentData.message,
                    updatedAt = System.currentTimeMillis().toString(),
                    createdAt = System.currentTimeMillis().toString(),
                    postId = commentData.postID,
                    id = commentData.commentID
                )
            )
            navigate("dashboard_screen")
        }
    ) {
        Text("OK")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview16() {
    TwitterTestTheme {
        CommentCompose(navController = rememberNavController())
    }
}

