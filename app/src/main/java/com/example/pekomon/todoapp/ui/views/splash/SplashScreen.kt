package com.example.pekomon.todoapp.ui.views.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.extensions.splashScreenBackground
import com.example.pekomon.todoapp.ui.theme.SPLASH_SCREEN_LOGO_SIZE
import com.example.pekomon.todoapp.ui.theme.ToDoAppTheme
import com.example.pekomon.todoapp.util.Consts
import com.example.pekomon.todoapp.util.Consts.SPLASH_SCREEN_DELAY_MILLIS
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (animationStarted) {
            0.dp
        } else {
            100.dp
        }
    )
    val alphaState  by animateFloatAsState(
        targetValue = if (animationStarted) {
            1f
        } else {
            0.2f
        },
        animationSpec = tween(
            durationMillis = Consts.LIST_ITEM_ANIMATION_DURATION_MILLIS / 2
        )
    )

    LaunchedEffect(key1 = true) {
        animationStarted = true
        delay(SPLASH_SCREEN_DELAY_MILLIS)
        navigateToListScreen()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(SPLASH_SCREEN_LOGO_SIZE)
                .offset(y = offsetState)
                .alpha(alpha = alphaState),
            painter = painterResource(id = getLogoForTheme()),
            contentDescription = stringResource(id = R.string.splash_screen_logo_content_description)
        )
    }
}

@Composable
fun getLogoForTheme(): Int {
    return if (isSystemInDarkTheme()) {
        R.drawable.ic_logo_dark
    } else {
        R.drawable.ic_logo_light
    }
}

@Composable
@Preview
fun PreviewSplashScreen() {
    SplashScreen(
        navigateToListScreen = {}
    )

}

@Composable
@Preview
fun PreviewSplashScreenDarkTheme() {
    ToDoAppTheme(darkTheme = true) {
        SplashScreen(
            navigateToListScreen = {}
        )
    }


}
