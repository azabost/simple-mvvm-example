package com.azabost.simplemvvm.ui.main

import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.net.ApiClient
import com.azabost.simplemvvm.net.GitHubService
import com.azabost.simplemvvm.net.response.RepoResponse
import com.azabost.simplemvvm.utils.HttpErrors
import com.azabost.simplemvvm.utils.getHttpException
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.mockito.Mockito
import pl.miensol.shouldko.shouldEqual

class MainViewModelTests {

    private val gitHubClient = Mockito.mock(GitHubService::class.java)
    private val apiClient = ApiClient(gitHubClient)
    private val testScheduler = TestScheduler()
    private val vm = MainViewModel(apiClient)

    private val progressObserver = TestObserver.create<Boolean>().apply {
        vm.progress.subscribe(this)
    }
    private val errorObserver = TestObserver.create<Int>().apply {
        vm.errors.subscribe(this)
    }
    private val showDataObserver = TestObserver.create<Unit>().apply {
        vm.showData.subscribe(this)
    }

    @Test
    fun getRepoShouldShowProgress() {
        val data = RepoResponse(12345)
        mockGetRepoResponse(data)

        vm.getRepo("any", "thing")
        progressObserver.assertValue(true)

        testScheduler.triggerActions()
        progressObserver.assertValueSequence(listOf(true, false))
    }

    @Test
    fun getRepoShouldNotShowError() {
        val data = RepoResponse(12345)
        mockGetRepoResponse(data)

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        errorObserver.assertEmpty()
    }

    @Test
    fun getRepoShouldShowData() {
        val data = RepoResponse(12345)
        mockGetRepoResponse(data)

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        showDataObserver.assertValueCount(1)
        vm.data.shouldEqual(data)
    }

    @Test
    fun getRepoErrorShouldShowHttpError() {
        mockGetRepoResponse(error = HttpErrors.getHttpException(404))

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(HttpErrors.DEFAULT_HTTP_ERROR_MESSAGE)
    }

    @Test
    fun getRepoErrorShouldShowDefaultError() {
        mockGetRepoResponse(error = Exception("Failed"))

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(R.string.default_error_message)
    }

    private fun mockGetRepoResponse(
        response: RepoResponse? = null,
        error: Throwable? = null
    ) {
        val r = if (response != null) Observable.just(response) else Observable.error(error)
        Mockito.`when`(
            gitHubClient.getRepo(
                Mockito.anyString(),
                Mockito.anyString()
            )
        ).thenReturn(r.subscribeOn(testScheduler).observeOn(testScheduler))
    }

}