package com.vlamik.news.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppBar(title: String, imageVector: ImageVector, backIconClickAction: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { backIconClickAction() }) {
                Icon(imageVector = imageVector, contentDescription = "")
            }
        }
    )
}

