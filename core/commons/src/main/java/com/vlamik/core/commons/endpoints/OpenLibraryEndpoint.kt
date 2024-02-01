package com.vlamik.core.commons.endpoints

object OpenLibraryEndpoint {
    val baseUrl: String
        get() = "www.balldontlie.io/api/v1"

    fun players(page: Int = 1, perPage: Int = 35) =
        "/players.json?page=$page&per_page=$perPage"

    fun player(id: Int) =
        "/players/$id.json"

    fun team(id: Int) =
        "/teams/$id.json"
}
