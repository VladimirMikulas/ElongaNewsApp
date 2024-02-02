@file:Suppress("MatchingDeclarationName")
package com.vlamik.nba.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlamik.nba.MainActivity.ViewModelFactoryProvider
import com.vlamik.nba.features.details.PlayerDetailsViewModel
import com.vlamik.nba.features.details.TeamDetailsViewModel
import dagger.hilt.android.EntryPointAccessors

interface BaseViewModelFactoryProvider {
    fun getPlayerDetailsViewModelFactory(): PlayerDetailsViewModel.Factory
    fun getTeamDetailsViewModelFactory(): TeamDetailsViewModel.Factory
}

@Composable
fun playerDetailViewModel(playerId: Int): PlayerDetailsViewModel = viewModel(
    factory = PlayerDetailsViewModel.provideFactory(
        getViewModelFactoryProvider().getPlayerDetailsViewModelFactory(),
        playerId
    )
)

@Composable
fun teamDetailViewModel(teamId: Int): TeamDetailsViewModel = viewModel(
    factory = TeamDetailsViewModel.provideFactory(
        getViewModelFactoryProvider().getTeamDetailsViewModelFactory(),
        teamId
    )
)

@Composable
private fun getViewModelFactoryProvider() = EntryPointAccessors.fromActivity(
    LocalContext.current as Activity,
    ViewModelFactoryProvider::class.java
)
