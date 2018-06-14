package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModel
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.net.ApiClient
import com.azabost.simplemvvm.net.response.RepoResponse
import com.azabost.simplemvvm.utils.logger
import com.azabost.simplemvvm.utils.showErrorMessages
import com.azabost.simplemvvm.utils.withProgress
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface MainVM {
    val progress: Observable<Boolean>
    val errors: Observable<Int>
    val data: Observable<RepoResponse>
    fun getRepoData(repoPath: String)
}

class MainViewModel @Inject constructor(
    private val apiClient: ApiClient
) : ViewModel(), MainVM {

    private var getRepoDataDisposable: Disposable? = null
    private val log = logger

    override val progress: PublishSubject<Boolean> = PublishSubject.create()
    override val errors: PublishSubject<Int> = PublishSubject.create()
    override val data: PublishSubject<RepoResponse> = PublishSubject.create()

    override fun getRepoData(repoPath: String) {
        val pathSplits = repoPath.split("/")

        if (pathSplits.size == 2) {
            val owner = pathSplits.first()
            val repo = pathSplits.last()
            fetchRepoData(owner, repo)
        } else {
            errors.onNext(R.string.wrong_repo_format)
        }
    }

    private fun fetchRepoData(owner: String, repo: String) {
        getRepoDataDisposable?.dispose()

        getRepoDataDisposable = apiClient.getRepo(owner, repo)
            .withProgress(progress)
            .showErrorMessages(errors, R.string.default_error_message) {
                when (it.code()) {
                    404 -> R.string.no_such_repo
                    else -> null
                }
            }
            .subscribe({
                data.onNext(it)
            }, {
                log.error("Fetching repo $owner / $repo failed", it)
            })
    }

    override fun onCleared() {
        getRepoDataDisposable?.dispose()
    }
}