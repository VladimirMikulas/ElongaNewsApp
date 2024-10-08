package com.vlamik.core.commons.endpoints

object OpenLibraryEndpoint {
    val baseUrl: String
        get() = "baseUrl"

    fun news(page: Int = 1, perPage: Int = 35) =
        "/news.json?page=$page&per_page=$perPage"

}
