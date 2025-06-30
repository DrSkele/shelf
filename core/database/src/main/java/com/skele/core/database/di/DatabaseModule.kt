package com.skele.core.database.di

import android.content.Context
import com.skele.core.database.TimerRoomDatabase
import com.skele.core.database.TodoRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTimerRoomDatabase(
        @ApplicationContext context: Context,
    ): TimerRoomDatabase =
        TimerRoomDatabase
            .createDatabase(context)

    @Provides
    @Singleton
    fun provideTodoRoomDatabase(
        @ApplicationContext context: Context,
    ): TodoRoomDatabase = TodoRoomDatabase.createDatabase(context)

    @Provides
    @Singleton
    fun provideTimerSettingsDao(database: TimerRoomDatabase) = database.timerSettingsDao()

    @Provides
    @Singleton
    fun provideTimerSessionDao(database: TimerRoomDatabase) = database.timerSessionDao()

    @Provides
    @Singleton
    fun provideToDoDao(database: TodoRoomDatabase) = database.todoDao()
}
