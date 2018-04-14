package com.azabost.simplemvvm.persistence

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideGitHubDatabase(context: Context): GitHubDatabase {
        return Room.databaseBuilder(context, GitHubDatabase::class.java, "GitHubDatabase.db")
            .build()
    }
}