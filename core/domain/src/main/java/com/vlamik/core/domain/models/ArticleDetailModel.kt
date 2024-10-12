package com.vlamik.core.domain.models

import com.vlamik.core.data.models.NewsDto


data class ArticleDetailModel(
    val imageUrl: String,
    val title: String,
    val description: String,
    val link: String,
    val publicationDate: String,
    val creators: String

)

fun NewsDto.toArticleDetailModel(): ArticleDetailModel = ArticleDetailModel(
    imageUrl = results.first().imageURL.orEmpty(),
    title = results.first().title.orEmpty(),
    description = results.first().description.orEmpty(),
    link = results.first().link.orEmpty(),
    publicationDate = "${results.first().pubDate.orEmpty()} ${results.first().pubDateTZ.orEmpty()}",
    creators = results.first().creator?.joinToString(", ").orEmpty()
)
