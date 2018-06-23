package com.azabost.simplemvvm.utils

import com.uber.autodispose.LifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.Observable

interface HasLifecycleScopeProvider {
    val scopeProvider: LifecycleScopeProvider<*>

    fun <T> Observable<T>.observeOnMainThreadAndAutoDispose() =
            this.observeOnMainThread().autoDisposable(scopeProvider)
}
