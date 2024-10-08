package com.vlamik.core.data.network

import com.vlamik.core.commons.BackgroundDispatcher
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OpenLibraryService
@Inject constructor(
    private val httpClient: OpenLibraryHttpClient,
    @BackgroundDispatcher private val coroutineContext: CoroutineContext
) {

    /*suspend fun getNews(page: Int): Result<NewsRecords> = withContext(coroutineContext) {
        return@withContext try {
            Result.success(
                httpClient().get {
                    url(path = OpenLibraryEndpoint.news(page))
                }.body()
            )
        } catch (e: Exception) {
            loge("Failed to get News", e)
            Result.failure(e)
        }
    }*/
}
