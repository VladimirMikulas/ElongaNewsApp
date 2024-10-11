package com.vlamik.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("article_id")
    val articleID: String,

    val title: String? = null,
    val link: String? = null,
    val keywords: List<String>? = null,
    val creator: List<String>? = null,

    @SerialName("video_url")
    val videoURL: String? = null,

    val description: String? = null,
    val content: String? = null,
    val pubDate: String? = null,
    val pubDateTZ: String? = null,

    @SerialName("image_url")
    val imageURL: String? = null,

    @SerialName("source_id")
    val sourceID: String? = null,

    @SerialName("source_priority")
    val sourcePriority: Long? = 0,

    @SerialName("source_name")
    val sourceName: String? = null,

    @SerialName("source_url")
    val sourceURL: String? = null,

    @SerialName("source_icon")
    val sourceIcon: String? = null,

    val language: String? = null,
    val country: List<String>? = null,
    val category: List<String>? = null,

    @SerialName("ai_tag")
    val aiTag: String? = null,

    val sentiment: String? = null,

    @SerialName("sentiment_stats")
    val sentimentStats: String? = null,

    @SerialName("ai_region")
    val aiRegion: String? = null,

    @SerialName("ai_org")
    val aiOrg: String? = null,

    val duplicate: Boolean? = false
)

