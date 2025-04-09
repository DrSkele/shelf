package com.skele.pomodoro.di

import com.skele.pomodoro.data.timer.DefaultTimerController
import com.skele.pomodoro.domain.timer.TimerController
import com.skele.pomodoro.util.DispatchersProvider
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
