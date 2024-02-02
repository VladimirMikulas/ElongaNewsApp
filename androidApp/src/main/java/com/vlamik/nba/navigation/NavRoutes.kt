package com.vlamik.nba.navigation

sealed class NavRoutes(internal open val path: String) {

    object PlayerList : NavRoutes("/")
    object PlayerDetails : NavRoutes("player_details/{$DETAILS_ID_KEY}") {
        fun build(id: Int): String =
            path.replace("{$DETAILS_ID_KEY}", id.toString())
    }

    object TeamDetails : NavRoutes("team_details/{$DETAILS_ID_KEY}") {
        fun build(id: Int): String =
            path.replace("{$DETAILS_ID_KEY}", id.toString())
    }

    companion object {
        const val DETAILS_ID_KEY: String = "id"
    }
}
