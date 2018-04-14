package com.azabost.simplemvvm.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ProgressHandler(private val progressSubject: PublishSubject<Boolean>) {
    private val log = logger

    private var ongoingOperations = 0

    fun onOperationStart() = synchronized(this) {
        ongoingOperations += 1
        showProgress()
    }

    fun onOperationEnd() = synchronized(this) {
        ongoingOperations -= 1

        if (ongoingOperations == 0) {
            hideProgress()
        }
    }

    private fun showProgress() {
        log.trace("Showing progress")
        progressSubject.onNext(true)
    }

    private fun hideProgress() {
        log.trace("Hiding progress")
        progressSubject.onNext(false)
    }
}

fun <T> Observable<T>.withProgress(
    progressHandler: ProgressHandler
): Observable<T> {

    return compose {
        it.doOnSubscribe {
            progressHandler.onOperationStart()
        }.doAfterTerminate {
            progressHandler.onOperationEnd()
        }
    }
}