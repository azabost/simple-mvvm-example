package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.response.CommitInfoResponse
import com.azabost.simplemvvm.net.response.RepoResponse
import com.azabost.simplemvvm.persistence.entities.UserEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Observable<RepoResponse>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Observable<List<CommitInfoResponse>>

    @GET("users/{name}")
    fun getUser(
        @Path("name") name: String
    ): Observable<UserEntity>

    companion object {
        val BASE_URL = "https://api.github.com/"
    }
}
