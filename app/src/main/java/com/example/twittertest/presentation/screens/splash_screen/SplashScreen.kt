package com.example.twittertest.presentation.screens.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.lib_data.util.Constants
import com.example.lib_data.util.ScreenConstants
import com.example.twittertest.R
import kotlinx.coroutines.delay

/**
 *
 */
@Composable
fun Splash() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.twitter_icon))
    LottieAnimation(composition)
}

/**
 *
 */
@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = .7f, animationSpec = tween(Constants.miliTimeTwo, easing = {
            OvershootInterpolator(2f).getInterpolation(it)
        }))
        delay(Constants.miliTimeOne)
        navController.navigate(ScreenConstants.homeScreen)
    }
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.twitter_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}
