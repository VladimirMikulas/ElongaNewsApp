package com.vlamik.core.domain

import com.vlamik.core.data.repositories.NewsRepository
import com.vlamik.core.domain.models.ArticleDetailModel
import com.vlamik.core.domain.models.toArticleDetailModel
import javax.inject.Inject

class GetArticle @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(id: String): Result<ArticleDetailModel> {
        return newsRepository.getArticle(id)
            .map { article -> article.toArticleDetailModel() }
    }

}
