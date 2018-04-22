package com.azabost.simplemvvm.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface CommitWithAuthorDao {

    @Query("SELECT * FROM commit_info INNER JOIN users ON users.id = author_id")
    fun getCommitsWithAuthors(): Flowable<List<CommitWithAuthor>>
}

data class CommitWithAuthor(
    val sha: String,
    val message: String,
    val login: String
)