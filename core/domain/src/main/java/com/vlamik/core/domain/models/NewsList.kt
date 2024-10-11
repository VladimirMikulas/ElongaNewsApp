package com.vlamik.core.domain.models

import com.vlamik.core.data.models.NewsDto


data class NewsList(
    val newsItems: List<NewsListItem>,
    val nextPage: String? = null,
)

fun NewsDto.toNewsListModel(): NewsList = NewsList(
    newsItems = results.map { it.toNewsItemModel() },
    nextPage = nextPage
)
