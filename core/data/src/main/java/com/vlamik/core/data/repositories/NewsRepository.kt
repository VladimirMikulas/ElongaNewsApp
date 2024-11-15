package com.vlamik.core.data.repositories

import com.vlamik.core.data.models.NewsDto

interface NewsRepository {
    suspend fun getNews(): Result<NewsDto>
    suspend fun getArticle(id: String): Result<NewsDto>
}