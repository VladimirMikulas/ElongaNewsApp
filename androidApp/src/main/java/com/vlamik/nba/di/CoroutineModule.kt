package com.vlamik.nba.di

import androidx.annotation.VisibleForTesting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.vlamik.core.commons.BackgroundDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
open class CoroutineModule {

    @VisibleForTesting
    internal open val backgroundDispatcher = Dispatchers.IO

    @Provides
    @BackgroundDispatcher
    open fun backgroundContext(): CoroutineContext = backgroundDispatcher
}
