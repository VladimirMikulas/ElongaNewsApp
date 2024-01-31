package com.vlamik.core.domain.models

import com.vlamik.core.data.models.PlayerDto

private const val KEY_PREFIX = "/works/"

data class Player(
    val key: String,
    val title: String
)

fun PlayerDto.toModel(): Player = Player(
    key = key.removePrefix(KEY_PREFIX),
    title = title
)
