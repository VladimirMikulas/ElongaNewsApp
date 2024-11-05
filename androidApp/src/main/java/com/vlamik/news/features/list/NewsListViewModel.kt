package com.vlamik.news.features.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlamik.core.domain.GetNews
import com.vlamik.core.domain.models.NewsListModel
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.ErrorFromAPI
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.LoadingFromAPI
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.NotAuthenticatedError
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.UpdateSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNews: GetNews
) : ViewModel() {

    private val _state = MutableStateFlow<ListScreenUiState>(LoadingFromAPI)
    val state = _state.asStateFlow()

    fun checkAuthentication(isAuthenticated: Boolean) {
        viewModelScope.launch {
            if (isAuthenticated) {
                if (_state.value !is UpdateSuccess) {
                    loadNews()
                }
            } else {
                _state.value = NotAuthenticatedError
            }
        }
    }

    private suspend fun loadNews() {
        getNews()
            .onSuccess {
                _state.value = UpdateSuccess(it)
            }
            .onFailure { _state.value = ErrorFromAPI }
    }

    sealed interface ListScreenUiState {
        object LoadingFromAPI : ListScreenUiState
        data class UpdateSuccess(val news: NewsListModel) : ListScreenUiState
        object ErrorFromAPI : ListScreenUiState
        object NotAuthenticatedError : ListScreenUiState

    }
}
