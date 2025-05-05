package com.skele.core.system.di

import com.skele.core.common.DispatchersProvider
import com.skele.core.system.DefaultTimerController
import com.skele.core.system.TimerController
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
    ): TimerController = DefaultTimerController(scope, dispatchersProvider)
}
