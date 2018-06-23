package com.azabost.simplemvvm.utils

import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

object MockitoUtils {

    // Workaround for Mockito's any() in Kotlin
    // https://medium.com/@elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791
    fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    fun <T> mockApiResponse(
        stubbing: OngoingStubbing<Observable<T>>,
        testScheduler: TestScheduler,
        response: T? = null,
        error: Throwable? = null
    ) {
        val r = if (response != null) Observable.just(response) else Observable.error(error)
        stubbing.thenReturn(r.subscribeOn(testScheduler).observeOn(testScheduler))
    }
}
