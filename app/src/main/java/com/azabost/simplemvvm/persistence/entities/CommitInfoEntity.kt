package com.azabost.simplemvvm.persistence.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.azabost.simplemvvm.net.response.CommitInfoResponse

@Entity(tableName = "commit_info")
data class CommitInfoEntity(
    @PrimaryKey val sha: String,

    val message: String,

    @ColumnInfo(name = AUTHOR_ID_COLUMN_NAME)
    val authorId: Long
) {
    companion object {
        const val AUTHOR_ID_COLUMN_NAME = "author_id"

        fun fromApiResponse(commitInfoResponse: CommitInfoResponse): CommitInfoEntity {
            return CommitInfoEntity(
                sha = commitInfoResponse.sha,
                authorId = commitInfoResponse.author.id,
                message = commitInfoResponse.commit.message
            )
        }
    }
}