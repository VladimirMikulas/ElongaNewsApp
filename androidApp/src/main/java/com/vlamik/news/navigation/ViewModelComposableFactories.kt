@file:Suppress("MatchingDeclarationName")

package com.vlamik.news.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlamik.news.MainActivity.ViewModelFactoryProvider
import com.vlamik.news.features.details.ArticleDetailViewModel
import dagger.hilt.android.EntryPointAccessors

interface BaseViewModelFactoryProvider {
    fun getArticleDetailViewModelFactory(): ArticleDetailViewModel.Factory
}

@Composable
fun articleDetailViewModel(articleId: String): ArticleDetailViewModel = viewModel(
    factory = ArticleDetailViewModel.provideFactory(
        getViewModelFactoryProvider().getArticleDetailViewModelFactory(), articleId
    )
)

@Composable
private fun getViewModelFactoryProvider() = EntryPointAccessors.fromActivity(
    LocalContext.current as Activity,
    ViewModelFactoryProvider::class.java
)
