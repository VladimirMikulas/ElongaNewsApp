package com.vlamik.news.features.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.vlamik.core.domain.models.NewsListItemModel
import com.vlamik.core.domain.models.NewsListModel
import com.vlamik.news.R
import com.vlamik.news.component.AppBar
import com.vlamik.news.component.LoadingIndicator
import com.vlamik.news.component.Toast
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.ErrorFromAPI
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.LoadingFromAPI
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.NotAuthenticatedError
import com.vlamik.news.features.list.NewsListViewModel.ListScreenUiState.UpdateSuccess
import com.vlamik.news.theme.Shapes
import com.vlamik.news.theme.TemplateTheme
import com.vlamik.news.utils.preview.DeviceFormatPreview
import com.vlamik.news.utils.preview.FontScalePreview
import com.vlamik.news.utils.preview.ThemeModePreview

@Composable
fun NewsListScreen(
    listViewModel: NewsListViewModel,
    isAuthenticated: Boolean,
    openDetailsClicked: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    listViewModel.checkAuthentication(isAuthenticated)
    val newsListUpdateState by listViewModel.state.collectAsState()
    when (val state = newsListUpdateState) {
        is UpdateSuccess, LoadingFromAPI, ErrorFromAPI, NotAuthenticatedError -> NewsListComposable(
            state = state,
            onDetailsClicked = openDetailsClicked,
            onBackClicked = onBackClicked
        )
    }
}

@Composable
private fun NewsListComposable(
    state: ListScreenUiState,
    onDetailsClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
) =
    Scaffold(topBar = {
        AppBar(
            title = stringResource(id = R.string.latest_news),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack
        ) { onBackClicked() }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            (state as? NotAuthenticatedError)?.let {
                ShowNotAuthenticatedError()
            }
            (state as? ErrorFromAPI)?.let {
                Toast(R.string.api_error)
            }
            (state as? LoadingFromAPI)?.let {
                LoadingIndicator()
            }
            var news by remember { mutableStateOf(emptyList<NewsListItemModel>()) }
            (state as? UpdateSuccess)?.let {
                news = it.news.newsItems

                LazyColumn {
                    itemsIndexed(news) { _, newsListItem ->

                        NewsListItemCard(newsListItem = newsListItem, onDetailsClicked)

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }
            }
        }
    }


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ArticleSourceImage(url: String) {
    GlideImage(
        model = url,
        loading = placeholder(R.drawable.news_logo),
        contentDescription = stringResource(id = R.string.article_source_image),
        modifier = Modifier
            .size(80.dp)
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
private fun NewsListItemContent(newsListItem: NewsListItemModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = newsListItem.title,
            fontSize = 18.sp
        )
        Text(
            text = newsListItem.publicationDate,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun NewsListItemCard(newsListItem: NewsListItemModel, onItemCardClicked: (String) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = Shapes.medium,
        modifier = Modifier.clickable { onItemCardClicked(newsListItem.id) }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize()
        ) {
            ArticleSourceImage(newsListItem.sourceIcon)
            NewsListItemContent(newsListItem = newsListItem)
        }
    }
}

@Composable
private fun ShowNotAuthenticatedError() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.not_authenticated_error),
            fontSize = 24.sp,
            color = Color.Red
        )

    }
}

@ThemeModePreview
@FontScalePreview
@DeviceFormatPreview
@Composable
private fun NewsListScreenPreview() {
    TemplateTheme {
        NewsListComposable(
            state = UpdateSuccess(
                NewsListModel(
                    (1..10).map {
                        NewsListItemModel(
                            "f006335711031c968e966703c634babb",
                            "Multitude Bank expands footprint with stake acquisition in Lea Bank",
                            "2024-10-11 11:36:00 UTC",
                            "https://i.bytvi.com/domain_icons/thepaypers.png"
                        )
                    }, nextPage = "1728646556050403349"
                )
            ),
            onDetailsClicked = {},
            onBackClicked = {})

    }
}
