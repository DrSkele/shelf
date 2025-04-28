package com.skele.pomodoro.di

import android.content.Context
import com.skele.pomodoro.data.DataStoreTimerRepository
import com.skele.pomodoro.domain.DefaultTimerController
import com.skele.pomodoro.domain.TimerController
import com.skele.pomodoro.data.TimerRepository
import com.skele.pomodoro.util.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideTimerRepository(
        @ApplicationContext context: Context,
    ): TimerRepository = DataStoreTimerRepository(context)
}
