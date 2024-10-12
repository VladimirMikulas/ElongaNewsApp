package com.vlamik.core.domain

import com.vlamik.core.data.repositories.NewsRepository
import com.vlamik.core.domain.models.NewsListModel
import com.vlamik.core.domain.models.toNewsListModel
import javax.inject.Inject

class GetNews @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Result<NewsListModel> {
        return newsRepository.getNews()
            .map { newsList -> newsList.toNewsListModel() }
    }

}
