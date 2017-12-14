package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.response.RepoResponse
import io.reactivex.Observable
import io.reactivex.Scheduler

class MockGitHubClient(
        val scheduler: Scheduler,
        var repoResponse: RepoResponse = RepoResponse(1),
        var error: Throwable? = null
) : GitHubClient {

    override fun getRepo(owner: String, repo: String): Observable<RepoResponse> {
        val response = error?.let { return Observable.error(it) } ?: Observable.just(repoResponse)
        return response.subscribeOn(scheduler).observeOn(scheduler)
    }
}