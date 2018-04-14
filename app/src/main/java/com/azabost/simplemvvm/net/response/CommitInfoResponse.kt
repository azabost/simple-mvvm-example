package com.azabost.simplemvvm.net.response

import com.azabost.simplemvvm.persistence.entities.UserEntity

data class CommitInfoResponse(
    val sha: String,
    val commit: Commit,
    val author: UserEntity
)

data class Commit(
    val message: String
)
