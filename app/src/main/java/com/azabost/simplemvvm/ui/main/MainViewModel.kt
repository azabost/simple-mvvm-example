package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModel
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.net.ApiClient
import com.azabost.simplemvvm.persistence.GitHubDatabase
import com.azabost.simplemvvm.persistence.dao.CommitWithAuthor
import com.azabost.simplemvvm.persistence.entities.CommitInfoEntity
import com.azabost.simplemvvm.utils.ProgressHandler
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
    val data: Observable<List<CommitWithAuthor>>
}

class MainViewModel @Inject constructor(
    private val gitHubClient: ApiClient,
    private val gitHubDatabase: GitHubDatabase
) : ViewModel(), MainVM, LoadingVM, DataVM {

    override val progress: PublishSubject<Boolean> = PublishSubject.create()
    override val errors: PublishSubject<Int> = PublishSubject.create()
    override val showData: PublishSubject<Unit> = PublishSubject.create()
    override var data: Observable<List<CommitWithAuthor>> =
        gitHubDatabase.commitWithAuthorDao().getCommitsWithAuthors().toObservable()

    private var getRepoDisposable: Disposable? = null
    private var getUsersDisposable: Disposable? = null

    private val usersToFetch = mutableSetOf<String>()
    private val progressHandler = ProgressHandler(progress)

    private val log = logger

    override fun getRepo(owner: String, repo: String) {
        getRepoDisposable?.dispose()

        usersToFetch.clear()

        getRepoDisposable = gitHubClient.getRepoCommits(owner, repo)
            .withProgress(progressHandler)
            .showErrorMessages(errors, R.string.default_error_message)
            .flatMapIterable { it }
            .subscribe({
                val newEntity = CommitInfoEntity.fromApiResponse(it)
                val existingEntity = gitHubDatabase.commitInfoDao().getCommitInfoBySha(it.sha)

                if (existingEntity == null) {
                    log.info("Adding commit info for SHA: ${it.sha}")
                    gitHubDatabase.commitInfoDao().addCommitInfo(newEntity)
                } else {
                    log.info("Commit info for SHA already exists: $existingEntity")
                }

                val existingUser = gitHubDatabase.userDao().getUserById(it.author.id)
                if (existingUser == null) {
                    log.info("Adding user to fetch: ${it.author.login}")
                    usersToFetch += it.author.login
                } else {
                    log.info("User already exists: ${existingUser.login}")
                }
            }, {
                log.error("Fetching repo failed", it)
            }, {
                log.info("Fetching repo completed")
                getUsers()
            })
    }

    private fun getUsers() {
        getUsersDisposable?.dispose()

        getUsersDisposable = Observable.fromIterable(usersToFetch)
            .flatMap {
                gitHubClient.getUser(it)
            }
            .withProgress(progressHandler)
            .showErrorMessages(errors, R.string.default_error_message)
            .subscribe({
                log.info("Adding user: ${it.login}")
                gitHubDatabase.userDao().addUser(it)
            }, {
                log.error("Fetching user failed", it)
            }, {
                showData.onNext(Unit)
            })
    }

    override fun onCleared() {
        getRepoDisposable?.dispose()
        getUsersDisposable?.dispose()
    }
}