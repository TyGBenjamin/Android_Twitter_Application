package com.example.twittertest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lib_data.util.Constants
import com.example.lib_data.util.ScreenConstants
import com.example.twittertest.presentation.screens.addPost.AddPost
import com.example.twittertest.presentation.screens.dashboard.Dashboard
import com.example.twittertest.presentation.screens.login.LoginPage
import com.example.twittertest.presentation.screens.sign_up.SignUpPage
import com.example.twittertest.presentation.screens.splash_screen.Splash
import com.example.twittertest.presentation.screens.splash_screen.SplashScreen
import com.example.twittertest.presentation.screens.view_post.PostDetail
import com.example.twittertest.ui.theme.TwitterTestTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwitterTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = Color(color = 0xFF202020),
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    Splash()
                    Navigation()
                }
            }
        }
    }
}

/**
 *
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TwitterTestTheme {
    }
}

/**
 *
 */
@SuppressLint("SuspiciousIndentation")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenConstants.splashScreen) {
        composable(ScreenConstants.splashScreen) {
            SplashScreen(navController = navController)
        }
        composable(ScreenConstants.homeScreen) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                ButtonWithIcon(navController)
            }
        }

        composable(ScreenConstants.loginScreen) {
            LoginPage {
                navController.navigate(it)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                toggleableLoginPrompt(isLogin = true) {
                    navController.navigate(it)
                }
            }
        }
        composable(ScreenConstants.signUpScreen) {
            SignUpPage {
                navController.navigate(it)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                toggleableLoginPrompt(isLogin = false) {
                    navController.navigate(it)
                }
            }
        }

        composable(ScreenConstants.dashboardScreen) {
            Dashboard(navController)
        }

        composable(ScreenConstants.addPostScreen) {
            AddPost(navController)
        }

        composable(ScreenConstants.addCommentScreen) {
            AddPost(navController)
        }

        composable("add_comment_screen/{id}") {
            val id = it.arguments?.getString("id")?.toInt()
            id?.let { it1 -> PostDetail(id = it1, navController = navController) }
            PostDetail(id = id, navController = navController)
        }

        composable(
            "view_post_screen/{id}",
        ) {
            val id = it.arguments?.getString("id")?.toInt()
            id?.let { it1 -> PostDetail(id = it1, navController = navController) }
            PostDetail(id = id, navController = navController)
        }
//         {
//            val id = it.arguments?.getString("id")?.toInt()
//            id?.let { it1 -> PostDetail(id = it1) }
//            PostDetail(id = id)
//        }
//        composable(
//            "view_post_screen/id",
//            arguments = listOf(navArgument("id") { type = NavType.IntType })
//        ) {
//            println(" ID IS HERE FOR NAV $id")
//            PostDetail(id = id)
//        }
    }
}


/**
 *
 */
@Composable
fun ButtonWithIcon(navController: NavController) {
    Button(
        onClick = {
            println("Login Button clicked")
            navController.navigate(ScreenConstants.loginScreen)
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
    ) {
        Image(
            painterResource(id = R.drawable.ic_baseline_login_24),
            contentDescription = "Cart button icon",
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = "Login", Modifier.padding(start = 10.dp), color = Color.White
        )


    }
}


/**
 *
 */
sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    /**
     *
     */
    object userName : InputType(
        label = Constants.username,
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    /**
     *
     */
    object password : InputType(
        label = Constants.password, icon = Icons.Default.Lock, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )

    /**
     *
     */
    object name : InputType(
        label = "Full Name",
        icon = Icons.Default.Add,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None

    )
}

/**
 *
 */
@Composable
fun TextInput(
    inputType: InputType, focusRequester: FocusRequester?,
    keyboardActions: KeyboardActions,
    changeValue: (value: String) -> String
) {
    var value: String by remember {
        mutableStateOf("")
    }
    TextField(
        value = value,
        onValueChange = { value = changeValue(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent


        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions

    )
}

/**
 *
 */
@Composable
fun toggleableLoginPrompt(
    isLogin: Boolean,
    navigate: (String) -> Unit
) {

    if (isLogin) {
        StatelessLoginTypeRow(
            loginTypeText = "Don't have an account?",
            navigate = navigate,
            loginType = "Sign Up",
            printedText = "Good job bitchesss..."
        )
    } else {
        StatelessLoginTypeRow(
            loginTypeText = "Already have an Account?",
            navigate = navigate,
            loginType = "Log In",
            printedText = "login (Text Button version) clicked"
        )
    }

}

@Composable
fun StatelessLoginTypeRow(
    loginTypeText: String,
    loginType: String,
    navigate: (String) -> Unit,
    printedText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = loginTypeText, color = Color.White)
        TextButton(onClick = {
            println(printedText) // Not hardcoded
            navigate(ScreenConstants.signUpScreen)
        }) {
            Text(loginType)  // context.getString('R.string.sing')
        }
    }
}



