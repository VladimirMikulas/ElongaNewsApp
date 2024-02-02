package com.vlamik.nba.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vlamik.core.domain.GetTeam
import com.vlamik.core.domain.models.Team
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamDetailsViewModel @AssistedInject constructor(
    @Assisted teamId: Int,
    getTeam: GetTeam,
) : ViewModel() {

    private val _updateState =
        MutableStateFlow<UiState>(UiState.LoadingFromAPI)
    val updateState = _updateState.asStateFlow()

    init {
        viewModelScope.launch {
            getTeam(teamId)
                .onSuccess { _updateState.value = UiState.Success(it) }
                .onFailure { _updateState.value = UiState.ErrorFromAPI }
        }
    }

    sealed interface UiState {
        object LoadingFromAPI : UiState
        data class Success(val team: Team) : UiState
        object ErrorFromAPI : UiState
    }

    @AssistedFactory
    interface Factory {
        fun create(teamId: Int): TeamDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            teamId: Int,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(teamId) as T
            }
        }
    }
}
