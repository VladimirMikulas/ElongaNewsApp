package com.vlamik.core.data.repositories

import com.vlamik.core.data.models.PlayerDetailsDto
import com.vlamik.core.data.models.PlayerRecords
import com.vlamik.core.data.models.TeamDetailsDto
import com.vlamik.core.data.network.OpenLibraryService
import javax.inject.Inject

class PlayerRepository
@Inject constructor(
    private val openLibraryService: OpenLibraryService
) {
    suspend fun getPlayers(page: Int=1): Result<PlayerRecords> =
        openLibraryService.getPlayers(page)

    suspend fun getPlayerDetails(id: Int): Result<PlayerDetailsDto> =
        openLibraryService.getPlayer(id)

    suspend fun getTeamDetails(id: Int): Result<TeamDetailsDto> =
        openLibraryService.getTeam(id)
}
