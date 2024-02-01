package com.vlamik.core.domain.models

import com.vlamik.core.data.models.PlayerDetailsDto

data class PlayerDetails(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val heightFeet: Int?,
    val heightInches: Int?,
    val weightPounds: Int?,
    val teamId: Int,
    val team: String,

    )

fun PlayerDetailsDto.toModel(): PlayerDetails = PlayerDetails(
    id = id,
    firstName = firstName,
    lastName= lastName,
    position = position,
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    teamId = team.id,
    team = team.fullName
)
