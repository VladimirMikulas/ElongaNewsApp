package com.vlamik.core.domain.models

import com.vlamik.core.data.models.PlayerDetailsDto
import com.vlamik.core.data.models.PlayerDto
import com.vlamik.core.data.models.TeamDetailsDto


data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val team: String
)

fun PlayerDetailsDto.toPlayerModel(): Player = Player(
    id = id,
    firstName = firstName,
    lastName= lastName,
    position = position,
    team = team.name
)
