package com.vlamik.core.data.network

import com.vlamik.core.commons.BackgroundDispatcher
import com.vlamik.core.commons.endpoints.OpenLibraryEndpoint
import com.vlamik.core.commons.loge
import com.vlamik.core.data.models.PlayerDetailsDto
import com.vlamik.core.data.models.PlayerRecords
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OpenLibraryService
@Inject constructor(
    private val httpClient: OpenLibraryHttpClient,
    @BackgroundDispatcher private val coroutineContext: CoroutineContext
) {

    suspend fun getPlayers(keyword: String): Result<PlayerRecords> = withContext(coroutineContext) {
        return@withContext try {
            Result.success(
                httpClient().get {
                    url(path = OpenLibraryEndpoint.search(keyword))
                }.body()
            )
        } catch (e: Exception) {
            loge("Failed to get Players", e)
            Result.failure(e)
        }
    }

    suspend fun getPlayer(id: String): Result<PlayerDetailsDto> = withContext(coroutineContext) {
        return@withContext try {
            Result.success(
                httpClient().get {
                    url(path = OpenLibraryEndpoint.work(id))
                }.body()
            )
        } catch (e: Exception) {
            loge("Failed to get Player", e)
            Result.failure(e)
        }
    }
}
