package com.vlamik.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRecords(
    @SerialName("numFound")
    val numFound: Int,
    @SerialName("docs")
    val docs: List<PlayerDto>,
)
