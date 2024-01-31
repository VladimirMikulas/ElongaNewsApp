package com.vlamik.nba.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.vlamik.core.domain.GetPlayer
import com.vlamik.core.domain.models.PlayerDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayersDetailsViewModel @AssistedInject constructor(
    @Assisted playerId: String,
    getPlayer: GetPlayer,
) : ViewModel() {

    private val _updateState =
        MutableStateFlow<UiState>(UiState.LoadingFromAPI)
    val updateState = _updateState.asStateFlow()

    init {
        viewModelScope.launch {
            getPlayer(playerId)
                .onSuccess { _updateState.value = UiState.Success(it) }
                .onFailure { _updateState.value = UiState.ErrorFromAPI }
        }
    }

    sealed interface UiState {
        object LoadingFromAPI : UiState
        data class Success(val player: PlayerDetails) : UiState
        object ErrorFromAPI : UiState
    }

    @AssistedFactory
    interface Factory {
        fun create(playerId: String): PlayersDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            playerId: String,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(playerId) as T
            }
        }
    }
}
