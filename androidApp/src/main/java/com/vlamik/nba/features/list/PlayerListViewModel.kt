package com.vlamik.nba.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlamik.core.commons.PublishFlow
import com.vlamik.core.domain.GetPlayers
import com.vlamik.core.domain.models.Player
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.ErrorFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.LoadingFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.LoadingMoreFromAPI
import com.vlamik.nba.features.list.PlayerListViewModel.ListScreenUiState.UpdateSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(
    private val getPlayers: GetPlayers
) : ViewModel() {

    private val events = PublishFlow<Event>()

    private val _state = MutableStateFlow<ListScreenUiState>(LoadingFromAPI)
    val state = _state.asStateFlow()
    private val playerLists = mutableListOf<Player>()
    private var pageNo = 1

    init {
        events
            .filterIsInstance<Event.Refresh>()
            .onStart { updatePlayerList() }
            .onEach {
                _state.value = LoadingFromAPI
                updatePlayerList()
            }.launchIn(viewModelScope)

        events
            .filterIsInstance<Event.LoadMore>()
            .onEach {
                _state.value = LoadingMoreFromAPI
                loadMorePlayers()
            }.launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch { events.emit(Event.Refresh) }
    }

    fun loadMore() {
        viewModelScope.launch { events.emit(Event.LoadMore) }
    }

    private suspend fun updatePlayerList() {
        getPlayers()
            .onSuccess {
                pageNo = 1
                playerLists.clear()
                playerLists.addAll(it)
                _state.value = UpdateSuccess(it)
            }
            .onFailure { _state.value = ErrorFromAPI }
    }

    private suspend fun loadMorePlayers() {
        getPlayers(pageNo)
            .onSuccess {
                pageNo++
                playerLists.addAll(it)
                _state.value = UpdateSuccess(playerLists)
            }
            .onFailure { _state.value = ErrorFromAPI }
    }


    private sealed class Event {
        object Refresh : Event()
        object LoadMore : Event()
    }

    sealed interface ListScreenUiState {
        object LoadingFromAPI : ListScreenUiState
        object LoadingMoreFromAPI : ListScreenUiState
        data class UpdateSuccess(val players: List<Player>) : ListScreenUiState
        object ErrorFromAPI : ListScreenUiState
    }
}
