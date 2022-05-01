package com.example.pekomon.todoapp.ui.views.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.extensions.splashScreenBackground
import com.example.pekomon.todoapp.ui.theme.SPLASH_SCREEN_LOGO_SIZE
import com.example.pekomon.todoapp.ui.theme.ToDoAppTheme

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(SPLASH_SCREEN_LOGO_SIZE),
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
    SplashScreen()

}

@Composable
@Preview
fun PreviewSplashScreenDarkTheme() {
    ToDoAppTheme(darkTheme = true) {
        SplashScreen()
    }


}
