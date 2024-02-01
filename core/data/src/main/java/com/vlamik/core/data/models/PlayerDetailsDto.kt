package com.vlamik.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDetailsDto(
    @SerialName("id")
    val id: Int,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("position")
    val position: String,
    @SerialName("height_feet")
    val heightFeet: Int?,
    @SerialName("height_inches")
    val heightInches: Int?,
    @SerialName("weight_pounds")
    val weightPounds: Int?,
    @SerialName("team")
    val team: TeamDetailsDto
)
