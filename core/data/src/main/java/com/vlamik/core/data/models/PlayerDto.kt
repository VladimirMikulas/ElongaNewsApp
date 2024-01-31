package com.vlamik.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("key")
    val key: String,
    @SerialName("title")
    val title: String
)
