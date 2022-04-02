package com.example.pekomon.todoapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.pekomon.todoapp.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    isVisible: Boolean,
    onClose: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    AnimatedVisibility(visible = isVisible) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )

            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmClicked()
                        onClose()
                    }
                ) {
                    Text(text = stringResource(id = R.string.generic_yes))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text(text = stringResource(id = R.string.generic_no))
                }
            },
            onDismissRequest = { onClose() }
        )
    }

}