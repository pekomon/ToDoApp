package com.example.pekomon.todoapp.extensions

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pekomon.todoapp.ui.theme.*

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else lightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700

val Colors.taskListItemBackGroundColor: Color
    @Composable
    get() = if (isLight) Color.White else darkGray

val Colors.taskListItemTextColor: Color
    @Composable
    get() = if (isLight) darkGray else lightGray
