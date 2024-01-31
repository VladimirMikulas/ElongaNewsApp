package com.vlamik.core.domain.models

import com.vlamik.core.data.models.PlayerDetailsDto

data class PlayerDetails(
    val title: String
)

fun PlayerDetailsDto.toModel(): PlayerDetails = PlayerDetails(
    title = title
)
