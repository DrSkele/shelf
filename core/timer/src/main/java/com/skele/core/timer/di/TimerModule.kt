package com.skele.core.timer.di

import com.skele.core.common.DispatchersProvider
import com.skele.core.common.TimeProvider
import com.skele.core.timer.DefaultTimerController
import com.skele.core.timer.TimerController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {
    @Provides
    @Singleton
    fun provideTimerController(
        scope: CoroutineScope,
        dispatchersProvider: DispatchersProvider,
        timeProvider: TimeProvider,
    ): TimerController = DefaultTimerController(scope, dispatchersProvider, timeProvider)
}
