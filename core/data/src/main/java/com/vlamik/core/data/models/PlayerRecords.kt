package com.vlamik.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRecords(
    @SerialName("data")
    val data: List<PlayerDetailsDto>,
    @SerialName("meta")
    val meta: PlayerRecordsMeta,
)
