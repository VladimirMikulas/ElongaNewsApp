package com.vlamik.core.domain.models

import com.vlamik.core.data.models.TeamDetailsDto


data class Team(
    val id: Int,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String
)

fun TeamDetailsDto.toModel(): Team = Team(
    id = id,
    city = city,
    conference= conference,
    division = division,
    fullName = fullName,
    name = name
)
