package com.example.pekomon.todoapp.ui.views.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pekomon.todoapp.R
import com.example.pekomon.todoapp.ui.theme.mediumGray

@Composable
fun EmptyListContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(140.dp),
            painter = painterResource(id = R.drawable.ic_baseline_sentiment_very_dissatisfied_24),
            contentDescription = stringResource(id = R.string.empty_list_sad_face_content_description),
            tint = mediumGray
        )
        Text(
            text = stringResource(id = R.string.empty_list_description),
            color = mediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}

@Composable
@Preview
fun PreviewEmptyListContent() {
    EmptyListContent()
}