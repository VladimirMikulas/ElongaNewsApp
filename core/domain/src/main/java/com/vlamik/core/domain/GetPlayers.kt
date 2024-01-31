package com.vlamik.core.domain

import com.vlamik.core.data.repositories.PlayerRepository
import com.vlamik.core.domain.models.Player
import com.vlamik.core.domain.models.toModel
import javax.inject.Inject

class GetPlayers @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(): Result<List<Player>> {
        return playerRepository.getPlayers()
            .map { it.docs }
            .map { playerList -> playerList.map { it.toModel() } }
    }
}
