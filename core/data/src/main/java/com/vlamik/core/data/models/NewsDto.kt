package com.vlamik.core.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val status: String,
    val totalResults: Long,
    val results: List<ArticleDto>,
    val nextPage: String? = null
)
