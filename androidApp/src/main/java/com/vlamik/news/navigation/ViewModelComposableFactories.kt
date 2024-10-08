@file:Suppress("MatchingDeclarationName")

package com.vlamik.news.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vlamik.news.MainActivity.ViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors

interface BaseViewModelFactoryProvider {
//    fun getNewsDetailsViewModelFactory(): NewsDetailsViewModel.Factory
}
/*
@Composable
fun playerDetailViewModel(playerId: Int): NewsDetailsViewModel = viewModel(
    factory = NewsDetailsViewModel.provideFactory(
        getViewModelFactoryProvider().getNewsDetailsViewModelFactory(),
        playerId
    )
)*/

@Composable
private fun getViewModelFactoryProvider() = EntryPointAccessors.fromActivity(
    LocalContext.current as Activity,
    ViewModelFactoryProvider::class.java
)
