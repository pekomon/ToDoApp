package com.example.pekomon.todoapp.extensions

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pekomon.todoapp.ui.theme.Purple500
import com.example.pekomon.todoapp.ui.theme.lightGray

val Colors.topAppBarColor: Color
    @Composable
    get() = if (isLight) Color.White else lightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black