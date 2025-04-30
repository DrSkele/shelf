package com.skele.core.database.di

import android.content.Context
import com.skele.core.database.AppRoomDatabase
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
    fun provideRoomDatabase(
        @ApplicationContext context: Context,
    ): AppRoomDatabase =
        AppRoomDatabase
            .createDatabase(context)

    @Provides
    @Singleton
    fun provideTimerSettingsDao(database: AppRoomDatabase) = database.timerSettingsDao()

    @Provides
    @Singleton
    fun provideTimerSessionDao(database: AppRoomDatabase) = database.timerSessionDao()
}
