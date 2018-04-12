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
    val showData: Observable<Unit>
    val progress: Observable<Boolean>
    val errors: Observable<Int>
}

interface LoadingVM {
    fun getRepo(owner: String, repo: String)
}

interface DataVM {
    val data: RepoResponse
}

class MainViewModel @Inject constructor(
        private val gitHubClient: ApiClient
) : ViewModel(), MainVM, LoadingVM, DataVM {

    override val progress: PublishSubject<Boolean> = PublishSubject.create()
    override val errors: PublishSubject<Int> = PublishSubject.create()
    override val showData: PublishSubject<Unit> = PublishSubject.create()
    override var data = RepoResponse(0)

    private var getRepoDisposable: Disposable? = null
    private val log = logger

    override fun getRepo(owner: String, repo: String) {
        getRepoDisposable?.dispose()

        getRepoDisposable = gitHubClient.getRepo(owner, repo)
                .withProgress(progress)
                .showErrorMessages(errors, R.string.default_error_message)
                .subscribe({
                    data = it
                    showData.onNext(Unit)
                }, {
                    log.error("Fetching repo failed", it)
                })
    }

    override fun onCleared() {
        getRepoDisposable?.dispose()
    }
}