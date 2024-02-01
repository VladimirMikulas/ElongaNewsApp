package com.vlamik.core.domain

import com.vlamik.core.commons.logd
import com.vlamik.core.data.repositories.PlayerRepository
import com.vlamik.core.domain.models.PlayerDetails
import com.vlamik.core.domain.models.Team
import com.vlamik.core.domain.models.toModel
import javax.inject.Inject

class GetTeam @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    suspend operator fun invoke(id: Int): Result<Team> {
        return playerRepository.getTeamDetails(id).map { teamDetails ->
            logd("Team Id: " + teamDetails.id)
            teamDetails.toModel()
        }
    }
}
