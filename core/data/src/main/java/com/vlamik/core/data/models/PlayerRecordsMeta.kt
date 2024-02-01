package com.vlamik.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRecordsMeta(
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("next_page")
    val nextPage: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_count")
    val totalCount: Int? = null
)
