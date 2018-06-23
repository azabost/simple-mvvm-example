package com.azabost.simplemvvm.utils

import android.support.annotation.StringRes
import com.azabost.simplemvvm.net.connectivity.ConnectivityException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

fun <T> Observable<T>.observeOnMainThread(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.withProgress(
    progressSubject: PublishSubject<Boolean>
): Observable<T> {

    return compose {
        it.doOnSubscribe {
            progressSubject.onNext(true)
        }.doAfterTerminate {
            progressSubject.onNext(false)
        }
    }
}

fun <T> Observable<T>.showErrorMessages(
    errorsSubject: PublishSubject<Int>,
    @StringRes default: Int,
    httpErrorsMapper: HttpErrorsMapper? = null
): Observable<T> {

    return compose {
        it.doOnError {
            if (it is HttpException) {
                errorsSubject.onNext(HttpErrors.httpExceptionToErrorMessage(it, httpErrorsMapper))
            } else if (it is ConnectivityException) {
                errorsSubject.onNext(it.reason)
            } else {
                errorsSubject.onNext(default)
            }
        }
    }
}