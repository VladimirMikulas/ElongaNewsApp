package com.vlamik.core.data.network

import com.vlamik.core.commons.ApiKey
import com.vlamik.core.commons.ApiKeyHeader
import com.vlamik.core.commons.BackgroundDispatcher
import com.vlamik.core.commons.endpoints.OpenLibraryEndpoint
import com.vlamik.core.commons.loge
import com.vlamik.core.data.models.NewsDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OpenLibraryService
@Inject constructor(
    private val httpClient: OpenLibraryHttpClient,
    @ApiKeyHeader private val apiKeyHeader: String,
    @ApiKey private val apiKey: String,
    @BackgroundDispatcher private val coroutineContext: CoroutineContext
) {


    suspend fun getNews(): Result<NewsDto> = withContext(coroutineContext) {
        return@withContext try {
            Result.success(
                httpClient().get {
                    header(apiKeyHeader, apiKey)
                    url(path = OpenLibraryEndpoint.news)
                }.body()
            )
        } catch (e: Exception) {
            loge("Failed to get News", e)
            Result.failure(e)
        }
    }

    suspend fun getArticle(id: String): Result<NewsDto> = withContext(coroutineContext) {
        return@withContext try {
            Result.success(
                httpClient().get {
                    header(apiKeyHeader, apiKey)
                    url(path = OpenLibraryEndpoint.article(id))
                }.body()
            )
        } catch (e: Exception) {
            loge("Failed to get Article", e)
            Result.failure(e)
        }
    }
}
