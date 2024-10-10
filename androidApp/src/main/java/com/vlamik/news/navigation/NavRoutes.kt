package com.vlamik.news.navigation

sealed class NavRoutes(internal open val path: String) {

    object Login : NavRoutes("/")
    object NewsList : NavRoutes("news_list/{$IS_AUTHENTICATED_KEY}") {
        fun build(isAuthenticated: Boolean): String =
            path.replace("{$IS_AUTHENTICATED_KEY}", isAuthenticated.toString())
    }

    object NewsDetails : NavRoutes("news_details/{$DETAILS_ID_KEY}") {
        fun build(id: Int): String =
            path.replace("{$DETAILS_ID_KEY}", id.toString())
    }

    companion object {
        const val IS_AUTHENTICATED_KEY = "isAuthenticated"
        const val DETAILS_ID_KEY: String = "id"
    }
}
