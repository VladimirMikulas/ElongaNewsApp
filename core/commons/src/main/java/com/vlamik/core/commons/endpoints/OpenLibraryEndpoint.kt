package com.vlamik.core.commons.endpoints

object OpenLibraryEndpoint {
    val baseUrl: String
        get() = "newsdata.io/api/1"

    val news: String
        get() = "latest?language=en"

}
