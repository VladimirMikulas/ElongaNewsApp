package com.vlamik.core.domain.models

import com.vlamik.core.data.models.NewsDto


data class NewsListModel(
    val newsItems: List<NewsListItemModel>,
    val nextPage: String? = null,
)

fun NewsDto.toNewsListModel(): NewsListModel = NewsListModel(
    newsItems = results.map { it.toNewsItemModel() },
    nextPage = nextPage
)
