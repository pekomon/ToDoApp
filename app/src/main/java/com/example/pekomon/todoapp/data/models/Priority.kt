package com.example.pekomon.todoapp.data.models

import androidx.compose.ui.graphics.Color
import com.example.pekomon.todoapp.ui.theme.HighPriority
import com.example.pekomon.todoapp.ui.theme.LowPriority
import com.example.pekomon.todoapp.ui.theme.MediumPriority
import com.example.pekomon.todoapp.ui.theme.NoPriority

enum class Priority(color: Color) {
    HIGH(HighPriority),
    MEDIUM(MediumPriority),
    LOW(LowPriority),
    NONE(NoPriority)
}