package com.vlamik.core.domain.models

import com.vlamik.core.data.models.ArticleDto


data class NewsListItemModel(
    val id: String,
    val title: String,
    val publicationDate: String,
    val sourceIcon: String
)

fun ArticleDto.toNewsItemModel(): NewsListItemModel = NewsListItemModel(
    id = articleID,
    title = title.orEmpty(),
    publicationDate = pubDate.orEmpty().plus(" ${pubDateTZ.orEmpty()}"),
    sourceIcon = sourceIcon.orEmpty(),
)
