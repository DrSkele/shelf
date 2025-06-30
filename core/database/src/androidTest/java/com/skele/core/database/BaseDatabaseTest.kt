package com.skele.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseDatabaseTest {
    // The in-memory database for testing
    protected lateinit var database: TimerRoomDatabase

    // Test dispatcher for coroutines
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    open fun setupDatabase() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Get the application context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create an in-memory database (the data is cleared when the process is killed)
        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    TimerRoomDatabase::class.java,
                ).allowMainThreadQueries() // Allow queries on main thread for testing
                .build()
    }

    @After
    fun closeDatabase() {
        database.close()
        Dispatchers.resetMain()
    }
}
