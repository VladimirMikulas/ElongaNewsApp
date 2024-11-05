package com.vlamik.news.features.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
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
    onBackClicked: () -> Unit,
    openLoginClicked: () -> Unit
) {
    listViewModel.checkAuthentication(isAuthenticated)
    val newsListUpdateState by listViewModel.state.collectAsState()

    NewsListComposable(
        state = newsListUpdateState,
        isAuthenticated = isAuthenticated,
        onDetailsClicked = openDetailsClicked,
        onBackClicked = onBackClicked,
        onLogoutClicked = openLoginClicked,
        onLoginClicked = openLoginClicked,
    )
}

@Composable
private fun NewsListComposable(
    state: ListScreenUiState,
    isAuthenticated: Boolean,
    onDetailsClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onLoginClicked: () -> Unit,
) {
    Scaffold(topBar = {
        AppBar(
            title = stringResource(id = R.string.latest_news),
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            backIconClickAction = onBackClicked,
            addLogoutButton = isAuthenticated,
            logoutClickAction = onLogoutClicked
        )
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                NotAuthenticatedError -> ShowNotAuthenticatedError(onLoginClicked)
                ErrorFromAPI -> Toast(R.string.api_error)
                LoadingFromAPI -> LoadingIndicator()
                is UpdateSuccess -> NewsListContent(news = state.news.newsItems, onDetailsClicked)
            }
        }
    }
}

@Composable
private fun NewsListContent(news: List<NewsListItemModel>, onDetailsClicked: (String) -> Unit) {
    if (news.isEmpty()) {
        Text(
            text = stringResource(id = R.string.no_news_available),
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn {
            itemsIndexed(news) { _, newsListItem ->
                NewsListItemCard(newsListItem = newsListItem, onDetailsClicked)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ArticleSourceImage(url: String) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
}

@Composable
private fun NewsListItemContent(newsListItem: NewsListItemModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = newsListItem.title,
            fontSize = 18.sp,
            maxLines = 3,
            minLines = 3
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
            NewsListItemContent(newsListItem)
        }
    }
}

@Composable
private fun ShowNotAuthenticatedError(
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.not_authenticated_error),
            fontSize = 24.sp,
            color = Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onLoginClicked() },
            colors = ButtonDefaults.buttonColors(containerColor = Red),
        ) {
            Text(text = stringResource(id = R.string.login_button), fontSize = 24.sp)
        }

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
            onBackClicked = {},
            isAuthenticated = true,
            onLogoutClicked = {},
            onLoginClicked = {})
    }
}
