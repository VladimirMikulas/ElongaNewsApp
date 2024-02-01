package com.vlamik.nba.features.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vlamik.core.domain.models.Player
import com.vlamik.nba.R
import com.vlamik.nba.component.Toast
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.ErrorFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.LoadingFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.UpdateSuccess
import com.vlamik.nba.theme.TemplateTheme
import com.vlamik.nba.theme.normalPadding
import com.vlamik.nba.utils.preview.DeviceFormatPreview
import com.vlamik.nba.utils.preview.FontScalePreview
import com.vlamik.nba.utils.preview.ThemeModePreview

@Composable
fun PlayerListScreen(
    listViewModel: PlayerListViewModel,
    openDetailsClicked: (Int) -> Unit,
) {
    val playerListUpdateState by listViewModel.state.collectAsState()

    when (val state = playerListUpdateState) {
        ErrorFromAPI -> ErrorFromApi()
        is UpdateSuccess, LoadingFromAPI -> ListPlayers(
            state = state,
            onRefresh = listViewModel::refresh,
            onDetailsClicked = openDetailsClicked,
        )
    }
}

@Composable
@Suppress("DEPRECATION") // SwipeRefresh migration not available in material 3 just for 2
private fun ListPlayers(
    state: ListScreenUiState,
    onRefresh: () -> Unit,
    onDetailsClicked: (Int) -> Unit,
) = SwipeRefresh(
    state = rememberSwipeRefreshState(state == LoadingFromAPI),
    onRefresh = onRefresh,
    modifier = Modifier
        .scrollable(rememberScrollState(), Orientation.Vertical)
        .systemBarsPadding()
        .padding(16.dp)
) {
    var players by remember { mutableStateOf(emptyList<Player>()) }
    (state as? UpdateSuccess)?.let {
        players = it.players
    }

    Column {
        Text(
            text = stringResource(id = R.string.player_list),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(normalPadding)
        )
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            itemsIndexed(players) { _, player ->
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetailsClicked(player.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 8.dp)
                    ) {
                        Text(text = player.firstName.plus(" ${player.lastName}"))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}

@Composable
private fun ErrorFromApi() = Toast(R.string.api_error)

@ThemeModePreview
@FontScalePreview
@DeviceFormatPreview
@Composable
private fun ListScreenPreview() {
    TemplateTheme {
        ListPlayers(
            state = UpdateSuccess(
                (1..10).map {
                    Player(237, "LeBron", "James", "F", "Lakers")
                }
            ),
            onRefresh = {}
        ) {}
    }
}
