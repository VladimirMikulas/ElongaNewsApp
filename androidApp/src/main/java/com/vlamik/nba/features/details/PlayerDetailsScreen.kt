package com.vlamik.nba.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlamik.core.domain.models.PlayerDetails
import com.vlamik.nba.R
import com.vlamik.nba.component.DetailItem
import com.vlamik.nba.component.Toast
import com.vlamik.nba.theme.Shapes
import com.vlamik.nba.theme.TemplateTheme
import com.vlamik.nba.theme.normalPadding
import com.vlamik.nba.utils.preview.DeviceFormatPreview
import com.vlamik.nba.utils.preview.FontScalePreview
import com.vlamik.nba.utils.preview.ThemeModePreview

@Composable
fun PlayerDetailsScreen(
    detailsViewModel: PlayerDetailsViewModel,
    openTeamDetailsClicked: (Int) -> Unit
) {
    val playerDetailsUpdateState by detailsViewModel.updateState.collectAsState()

    when (val state = playerDetailsUpdateState) {
        PlayerDetailsViewModel.UiState.ErrorFromAPI -> Toast(R.string.api_error)
        PlayerDetailsViewModel.UiState.LoadingFromAPI -> Unit
        is PlayerDetailsViewModel.UiState.Success -> {
            PlayerDetailsUi(
                state = state,
                openTeamDetailClicked = openTeamDetailsClicked
            )
        }
    }
}

@Composable
private fun PlayerDetailsUi(
    state: PlayerDetailsViewModel.UiState,
    openTeamDetailClicked: (Int) -> Unit
) =
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        val playerDetails: PlayerDetails
        (state as? PlayerDetailsViewModel.UiState.Success)?.let {
            playerDetails = it.player

            Text(
                text = playerDetails.firstName.plus(" ${playerDetails.lastName}"),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(normalPadding)
            )
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = Shapes.large,
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        DetailItem(
                            titleResId = R.string.player_detail_position,
                            value = playerDetails.position
                        )
                        DetailItem(
                            titleResId = R.string.player_detail_height_feet,
                            value = playerDetails.heightFeet.toString()
                        )
                        DetailItem(
                            titleResId = R.string.player_detail_height_inches,
                            value = playerDetails.heightInches.toString()
                        )
                        DetailItem(
                            titleResId = R.string.player_detail_weight_pounds,
                            value = playerDetails.weightPounds.toString()
                        )
                        DetailItem(
                            titleResId = R.string.player_detail_team,
                            value = playerDetails.team
                        )
                        Button(onClick = { openTeamDetailClicked(playerDetails.teamId) }) {
                            Text(text = stringResource(id = R.string.team_detail_button))

                        }
                    }
                }
            }
        }
    }

@ThemeModePreview
@FontScalePreview
@DeviceFormatPreview
@Composable
private fun ListScreenPreview() {
    TemplateTheme {
        PlayerDetailsUi(
            state = PlayerDetailsViewModel.UiState.Success(
                PlayerDetails(237, "LeBron", "James", "F", 6, 3, 200, 10, "Lakers")
            ),
            openTeamDetailClicked = {}
        )
    }
}

