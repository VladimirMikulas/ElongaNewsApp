package com.vlamik.core.domain.models

import com.vlamik.core.data.models.ArticleDto


data class NewsListItem(
    val id: String,
    val title: String,
    val publicationDate: String,
    val sourceIcon: String
)

fun ArticleDto.toNewsItemModel(): NewsListItem = NewsListItem(
    id = articleID,
    title = title.orEmpty(),
    publicationDate = pubDate.orEmpty().plus(" ${pubDateTZ.orEmpty()}"),
    sourceIcon = sourceIcon.orEmpty(),
)
