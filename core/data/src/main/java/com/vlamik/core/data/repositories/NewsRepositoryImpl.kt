package com.vlamik.core.data.repositories

import com.vlamik.core.data.models.NewsDto
import com.vlamik.core.data.network.OpenLibraryService
import javax.inject.Inject

class NewsRepositoryImpl
@Inject constructor(
    private val openLibraryService: OpenLibraryService
) : NewsRepository {
    override suspend fun getNews(): Result<NewsDto> =
        openLibraryService.getNews()

}
