@file:Suppress("MatchingDeclarationName")
package com.vlamik.nba.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.EntryPointAccessors
import com.vlamik.nba.MainActivity.ViewModelFactoryProvider
import com.vlamik.nba.features.details.PlayersDetailsViewModel

interface BaseViewModelFactoryProvider {
    fun getDetailsViewModelFactory(): PlayersDetailsViewModel.Factory
}

@Composable
fun detailViewModel(playerId: String): PlayersDetailsViewModel = viewModel(
    factory = PlayersDetailsViewModel.provideFactory(
        getViewModelFactoryProvider().getDetailsViewModelFactory(),
        playerId
    )
)

@Composable
private fun getViewModelFactoryProvider() = EntryPointAccessors.fromActivity(
    LocalContext.current as Activity,
    ViewModelFactoryProvider::class.java
)
