package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.connectivity.Connectivity
import com.azabost.simplemvvm.net.connectivity.ConnectivityException
import com.azabost.simplemvvm.net.response.RepoResponse
import io.reactivex.Observable
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val gitHubService: GitHubService,
    private val connectivity: Connectivity
) {
    fun getRepo(owner: String, repo: String): Observable<RepoResponse> {
        return gitHubService.getRepo(owner, repo).checkConnectivity()
    }

    private fun <T> Observable<T>.checkConnectivity(): Observable<T> {
        return if (connectivity.hasInternetAccess()) {
            this
        } else {
            Observable.error(ConnectivityException())
        }
    }
}