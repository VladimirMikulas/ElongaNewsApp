package com.vlamik.core.data.repositories

import com.vlamik.core.data.models.PlayerDetailsDto
import com.vlamik.core.data.models.PlayerRecords
import com.vlamik.core.data.network.OpenLibraryService
import javax.inject.Inject

class PlayerRepository
@Inject constructor(
    private val openLibraryService: OpenLibraryService
) {
    suspend fun getPlayers(keyword: String = "Android"): Result<PlayerRecords> =
        openLibraryService.getPlayers(keyword)

    suspend fun getPlayerDetails(id: String): Result<PlayerDetailsDto> =
        openLibraryService.getPlayer(id)
}
