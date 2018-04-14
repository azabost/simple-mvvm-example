package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.response.CommitInfoResponse
import com.azabost.simplemvvm.net.response.RepoResponse
import com.azabost.simplemvvm.persistence.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class ApiClient @Inject constructor(private val gitHubService: GitHubService) {

    fun getRepo(owner: String, repo: String): Observable<RepoResponse> {
        return gitHubService.getRepo(owner, repo)
    }

    fun getRepoCommits(owner: String, repo: String): Observable<List<CommitInfoResponse>> {
        return gitHubService.getCommits(owner, repo)
    }

    fun getUser(name: String): Observable<UserEntity> {
        return gitHubService.getUser(name)
    }

}