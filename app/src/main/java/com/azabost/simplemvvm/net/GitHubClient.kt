package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.response.RepoResponse
import io.reactivex.Observable

interface GitHubClient {
    fun getRepo(owner: String, repo: String): Observable<RepoResponse>
}