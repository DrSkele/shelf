package com.skele.core.stopwatch.di

import com.skele.core.common.DispatchersProvider
import com.skele.core.stopwatch.DefaultStopwatchController
import com.skele.core.stopwatch.StopwatchController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StopwatchModule {
    @Provides
    @Singleton
    fun provideStopwatchController(
        scope: CoroutineScope,
        dispatchersProvider: DispatchersProvider,
    ): StopwatchController = DefaultStopwatchController(scope, dispatchersProvider)
}
