package com.vlamik.news.features.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.vlamik.core.domain.models.ArticleDetailModel
import com.vlamik.news.R
import com.vlamik.news.component.AppBar
import com.vlamik.news.component.DetailItem
import com.vlamik.news.component.LoadingIndicator
import com.vlamik.news.component.Toast
import com.vlamik.news.theme.Shapes
import com.vlamik.news.theme.TemplateTheme
import com.vlamik.news.theme.normalPadding
import com.vlamik.news.utils.preview.DeviceFormatPreview
import com.vlamik.news.utils.preview.FontScalePreview
import com.vlamik.news.utils.preview.ThemeModePreview

@Composable
fun ArticleDetailScreen(
    detailsViewModel: ArticleDetailViewModel,
    onBackClicked: () -> Unit,
) {
    val articleDetailState by detailsViewModel.updateState.collectAsState()

    when (val state = articleDetailState) {
        ArticleDetailViewModel.UiState.ErrorFromAPI -> Toast(R.string.api_error)
        ArticleDetailViewModel.UiState.LoadingFromAPI -> LoadingIndicator()
        is ArticleDetailViewModel.UiState.Success -> {
            ArticleDetailUi(
                state = state,
                onBackClicked = onBackClicked
            )
        }
    }
}

@Composable
private fun ArticleDetailUi(
    state: ArticleDetailViewModel.UiState,
    onBackClicked: () -> Unit
) {
    val articleDetail: ArticleDetailModel
    (state as? ArticleDetailViewModel.UiState.Success)?.let {
        articleDetail = it.article

        Scaffold(topBar = {
            AppBar(
                title = stringResource(id = R.string.article_detail),
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                shareData = articleDetail.link,
                backIconClickAction = { onBackClicked() })
        }) { contentPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ArticlePicture(articleDetail.imageUrl)
                    ArticleContent(articleDetail = articleDetail)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticlePicture(articleImageUrl: String) {
    ElevatedCard(
        shape = RectangleShape,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = articleImageUrl,
                loading = placeholder(R.drawable.news_logo),
                contentDescription = stringResource(id = R.string.article_image),
                modifier = Modifier
                    .size(350.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ArticleContent(articleDetail: ArticleDetailModel) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = Shapes.large,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(normalPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = articleDetail.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(normalPadding)
            )
            Text(
                text = articleDetail.description,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(normalPadding)
            )

            if (articleDetail.creators.isNotEmpty()) {
                DetailItem(
                    titleResId = R.string.creators,
                    value = articleDetail.creators
                )
            }
            if (articleDetail.publicationDate.isNotEmpty()) {
                DetailItem(
                    titleResId = R.string.publication_date,
                    value = articleDetail.publicationDate
                )
            }
        }
    }

}

@ThemeModePreview
@FontScalePreview
@DeviceFormatPreview
@Composable
private fun ArticleDetailPreview() {
    TemplateTheme {
        ArticleDetailUi(
            state = ArticleDetailViewModel.UiState.Success(
                ArticleDetailModel(
                    imageUrl = "https://www.manilatimes.net/manilatimes/uploads/images/2024/10/11/434513.jpg",
                    title = "The freedom to run",
                    description = "\"SECTION 69 of Batas Pambansa 881, also known as the Omnibus " +
                            "Election Code, has defined who are the nuisance candidates whose " +
                            "certificates of candidacy the Commission on Elections (Comelec) can " +
                            "refuse to give due course to or cancel either through its own " +
                            "initiative or upon a verified petition of an interested party." +
                            "The grounds are: if it is shown that the candidates have the intention " +
                            "of mocking the electoral process, or of putting it in disrepute, or " +
                            "cause confusion among the voters by the similarity of the names of " +
                            "the registered candidates, or when it is clearly demonstrated that " +
                            "the candidate has no bona fide intention to run for the office for " +
                            "which the certificate of candidacy has been filed....",
                    link = "https://www.manilatimes.net/2024/10/12/opinion/columns/the-freedom-to-run/1983213",
                    publicationDate = "2024-10-11 16:09:00 UTC",
                    creators = "Antonio Contreras"
                )
            ),
            onBackClicked = {}
        )
    }
}

