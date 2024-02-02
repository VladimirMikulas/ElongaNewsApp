package com.vlamik.nba.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlamik.nba.R
import com.vlamik.nba.component.Toast

@Composable
fun PlayerDetailsScreen(detailsViewModel: PlayerDetailsViewModel) {
    val playerListUpdateState by detailsViewModel.updateState.collectAsState()

    when (val state = playerListUpdateState) {
        PlayerDetailsViewModel.UiState.ErrorFromAPI -> Toast(R.string.api_error)
        PlayerDetailsViewModel.UiState.LoadingFromAPI -> Unit
        is PlayerDetailsViewModel.UiState.Success -> {
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(16.dp)
            ) {
                Text(
                    text = state.player.firstName.plus(" ${state.player.lastName}"),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}
