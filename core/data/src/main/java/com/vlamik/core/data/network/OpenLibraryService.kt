package com.vlamik.core.data.network

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
    @BackgroundDispatcher private val coroutineContext: CoroutineContext
) {
    companion object {
        private const val API_KEY_HEADER = "X-ACCESS-KEY"
        private const val API_KEY_VALUE = "YOUR_API_KEY"
    }

    suspend fun getNews(): Result<NewsDto> = withContext(coroutineContext) {
        return@withContext try {
            Result.success(
                httpClient().get {
                    header(API_KEY_HEADER, API_KEY_VALUE)
                    url(path = OpenLibraryEndpoint.news)
                }.body()
            )
        } catch (e: Exception) {
            loge("Failed to get News", e)
            Result.failure(e)
        }
    }
}
