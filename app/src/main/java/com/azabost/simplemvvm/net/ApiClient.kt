package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.response.RepoResponse
import io.reactivex.Observable
import javax.inject.Inject

class ApiClient @Inject constructor(private val gitHubService: GitHubService) : GitHubClient {
    override fun getRepo(owner: String, repo: String): Observable<RepoResponse> {
        return gitHubService.getRepo(owner, repo)
    }
}