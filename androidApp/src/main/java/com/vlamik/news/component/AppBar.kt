package com.vlamik.news.component

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.vlamik.news.R

private const val SHARE_DATA_TYPE = "text/plain"

@Composable
fun AppBar(
    title: String,
    navigationIcon: ImageVector,
    shareData: String = "",
    backIconClickAction: () -> Unit,
    addLogoutButton: Boolean = false,
    logoutClickAction: () -> Unit = {},
) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { backIconClickAction() }) {
                Icon(imageVector = navigationIcon, contentDescription = "")
            }
        }, actions = {
            if (shareData.isNotEmpty()) {
                IconButton(onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = SHARE_DATA_TYPE
                        putExtra(Intent.EXTRA_TEXT, shareData)
                    }
                    val chooserIntent = Intent.createChooser(
                        shareIntent,
                        context.resources.getString(R.string.share)
                    )
                    context.startActivity(chooserIntent)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = stringResource(id = R.string.share_via)
                    )
                }
            }
            if (addLogoutButton) {
                TextButton(onClick = { logoutClickAction() }) {
                    Text(text = "Logout")
                }
            }
        }
    )
}

