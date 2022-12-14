package com.example.twittertest.presentation.screens.sign_up

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lib_data.util.Resource
import com.example.lib_data.util.ScreenConstants
import com.example.twittertest.InputType
import com.example.twittertest.R
import com.example.twittertest.TextInput

/**
 *
 */
@Composable
fun SignUpPage(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigate: (String) -> Unit

) {
    val tag = "SignUpPage"
    val token = viewModel.user.collectAsState().value


    val passwordFocusRequester = FocusRequester()
    val focusManager: FocusManager = LocalFocusManager.current

    val signUpActions = SignUpPageData(
        nameKeyboardAction = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
        userNameKeyboardAction = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
        passwordKeyboardAction = KeyboardActions(onDone = { focusManager.clearFocus() }),
        passwordFocusRequester = passwordFocusRequester
    )
    val signUpEvents = SignUpPageEvents(
        setName = { viewModel.setName(it) },
        setUsername = { viewModel.setUsername(it) },
        setPassword = { viewModel.setPassword(it) },
        saveUser = { viewModel.saveUser() },
        navigate = { navigate(it) }
    )


    SignUpPageContent(
        data = signUpActions,
        events = signUpEvents
    )


    LaunchedEffect(key1 = token) {
        when (token) {
            is Resource.Error -> {}
            Resource.Loading -> {}
            is Resource.Success -> navigate(ScreenConstants.loginScreen)
            else -> Log.e(tag, "token launched with unexpected value.")

        }
    }
}

/**
 *
 */
data class SignUpPageData(
    val nameKeyboardAction: KeyboardActions,
    val userNameKeyboardAction: KeyboardActions,
    val passwordKeyboardAction: KeyboardActions,
    val passwordFocusRequester: FocusRequester?
)

/**
 *
 */
data class SignUpPageEvents(
    val setName: (String) -> Unit,
    val setUsername: (String) -> Unit,
    val setPassword: (String) -> Unit,
    val saveUser: () -> Unit,
    val navigate: (String) -> Unit
)


@Composable
fun SignUpPageContent(
    data: SignUpPageData,
    events: SignUpPageEvents
) {
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(13.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconOne()
        TextInput(
            InputType.name,
            keyboardActions = data.nameKeyboardAction,
            focusRequester = null
        ) { name ->
            events.setName(name)
            name
        }
        TextInput(
            InputType.userName,
            keyboardActions = data.userNameKeyboardAction,
            focusRequester = null
        ) { username ->
            events.setUsername(username)
            username
        }
        TextInput(
            InputType.password,
            keyboardActions = data.passwordKeyboardAction,
            focusRequester = data.passwordFocusRequester
        ) { password ->
            events.setPassword(password)
            password
        }
        Button(
            onClick = {
                events.saveUser()
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Sign Up", modifier = Modifier.padding(vertical = .8.dp))
            DividerOne()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?", color = Color.White)
                TextButton(onClick = {
                    println("Sign Up Button clicked")
                    events.navigate("signUp_screen")
                }) {
                    Text("Sign Up")
                }
            }
        }
    }
}

/**
 *
 */
@Composable
fun DividerOne() {
    Divider(
        color = Color.White.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(top = 48.dp)
    )
}

/**
 *
 */
@Composable
fun IconOne() {
    Icon(
        painter = painterResource(id = R.drawable.twitter_logo),
        null,
        Modifier.size(80.dp),
        tint = Color.White
    )
}
