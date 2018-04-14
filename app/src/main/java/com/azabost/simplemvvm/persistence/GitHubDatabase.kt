package com.azabost.simplemvvm.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.azabost.simplemvvm.persistence.dao.CommitInfoDao
import com.azabost.simplemvvm.persistence.dao.UserDao
import com.azabost.simplemvvm.persistence.entities.CommitInfoEntity
import com.azabost.simplemvvm.persistence.entities.UserEntity

@Database(
    entities = [
        CommitInfoEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class GitHubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun commitInfoDao(): CommitInfoDao
}