package com.vlamik.news.navigation

sealed class NavRoutes(internal open val path: String) {

    object NewsList : NavRoutes("/")
    object NewsDetails : NavRoutes("news_details/{$DETAILS_ID_KEY}") {
        fun build(id: Int): String =
            path.replace("{$DETAILS_ID_KEY}", id.toString())
    }

    companion object {
        const val DETAILS_ID_KEY: String = "id"
    }
}
