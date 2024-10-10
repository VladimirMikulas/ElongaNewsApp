package com.vlamik.news.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlamik.core.commons.PublishFlow
import com.vlamik.core.domain.GetAuthentication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthentication: GetAuthentication
) : ViewModel() {

    private val events = PublishFlow<Event>()
    private val _state =
        MutableStateFlow<LoginUiState>(LoginUiState.InitialState)
    val state = _state.asStateFlow()

    init {
        events
            .filterIsInstance<Event.Authenticate>()
            .onEach {
                authenticate(it.email, it.password)
            }.launchIn(viewModelScope)
    }

    private fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            _state.value = if (getAuthentication(email, password)) {
                LoginUiState.LoginSuccess
            } else {
                LoginUiState.IncorrectPassword
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch { events.emit(Event.Authenticate(email, password)) }
    }

    sealed interface LoginUiState {
        object InitialState : LoginUiState
        object LoginSuccess : LoginUiState
        object IncorrectPassword : LoginUiState
    }

    private sealed class Event {
        data class Authenticate(val email: String, val password: String) : Event()
    }

}
