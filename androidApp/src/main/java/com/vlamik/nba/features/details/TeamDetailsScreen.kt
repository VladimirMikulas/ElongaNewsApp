package com.vlamik.nba.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlamik.core.domain.models.Team
import com.vlamik.nba.R
import com.vlamik.nba.component.Toast
import com.vlamik.nba.theme.Shapes
import com.vlamik.nba.theme.TemplateTheme
import com.vlamik.nba.theme.normalPadding
import com.vlamik.nba.utils.preview.DeviceFormatPreview
import com.vlamik.nba.utils.preview.FontScalePreview
import com.vlamik.nba.utils.preview.ThemeModePreview

@Composable
fun TeamDetailsScreen(
    detailsViewModel: TeamDetailsViewModel
) {
    val teamDetailsUpdateState by detailsViewModel.updateState.collectAsState()

    when (val state = teamDetailsUpdateState) {
        TeamDetailsViewModel.UiState.ErrorFromAPI -> Toast(R.string.api_error)
        TeamDetailsViewModel.UiState.LoadingFromAPI -> Unit
        is TeamDetailsViewModel.UiState.Success -> {
            TeamDetailsUi(
                state = state
            )
        }
    }
}

@Composable
private fun TeamDetailsUi(
    state: TeamDetailsViewModel.UiState
) =
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        val teamDetails: Team
        (state as? TeamDetailsViewModel.UiState.Success)?.let {
            teamDetails = it.team

            Text(
                text = teamDetails.fullName,
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
                            titleResId = R.string.team_detail_city,
                            value = teamDetails.city
                        )
                        DetailItem(
                            titleResId = R.string.team_detail_conference,
                            value = teamDetails.conference
                        )
                        DetailItem(
                            titleResId = R.string.team_detail_division,
                            value = teamDetails.division
                        )
                    }
                }
            }
        }
    }

@Composable
private fun DetailItem(titleResId: Int, value: String) {
    Text(
        text = stringResource(id = titleResId),
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 0.dp)
    )
    Text(
        text = value,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 0.dp, end = 0.dp, bottom = 16.dp)
    )

}

@ThemeModePreview
@FontScalePreview
@DeviceFormatPreview
@Composable
private fun ListScreenPreview() {
    TemplateTheme {
        TeamDetailsUi(
            state = TeamDetailsViewModel.UiState.Success(
                Team(14, "Los Angeles", "West", "Pacific", "Los Angeles Lakers", "Lakers")
            )
        )
    }
}

