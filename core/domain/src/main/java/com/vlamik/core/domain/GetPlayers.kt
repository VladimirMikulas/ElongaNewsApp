package com.vlamik.core.domain

import com.vlamik.core.data.repositories.PlayerRepository
import com.vlamik.core.domain.models.Player
import com.vlamik.core.domain.models.toModel
import com.vlamik.core.domain.models.toPlayerModel
import javax.inject.Inject

class GetPlayers @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(currentPage: Int = 0): Result<List<Player>> {
        return playerRepository.getPlayers(currentPage + 1)
            .map { it.data }
            .map { playerList -> playerList.map { it.toPlayerModel() } }
    }

}
