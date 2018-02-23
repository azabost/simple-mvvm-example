package com.azabost.simplemvvm.ui.main

import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.net.MockGitHubClient
import com.azabost.simplemvvm.net.response.RepoResponse
import com.azabost.simplemvvm.utils.HttpErrors
import com.azabost.simplemvvm.utils.getHttpException
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class MainViewModelTests {
    lateinit var vm: MainViewModel
    lateinit var gitHubClient: MockGitHubClient
    lateinit var testScheduler: TestScheduler
    lateinit var progressObserver: TestObserver<Boolean>
    lateinit var errorObserver: TestObserver<Int>
    lateinit var showDataObserver: TestObserver<Unit>

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        gitHubClient = MockGitHubClient(testScheduler)
        vm = MainViewModel(gitHubClient)
        setupObservers(vm)
    }

    fun setupObservers(vm: MainViewModel) {
        progressObserver = TestObserver.create()
        vm.progress.subscribe(progressObserver)
        errorObserver = TestObserver.create()
        vm.errors.subscribe(errorObserver)
        showDataObserver = TestObserver.create()
        vm.showData.subscribe(showDataObserver)
    }

    @After
    fun tearDown() {
        testScheduler.shutdown()
        progressObserver.dispose()
        errorObserver.dispose()
        showDataObserver.dispose()
    }

    @Test
    fun getRepoShouldShowProgress() {
        vm.getRepo("any", "thing")
        progressObserver.assertValue(true)

        testScheduler.triggerActions()
        progressObserver.assertValueSequence(listOf(true, false))
    }

    @Test
    fun getRepoShouldNotShowError() {
        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        errorObserver.assertEmpty()
    }

    @Test
    fun getRepoShouldShowData() {
        val data = RepoResponse(12345)
        gitHubClient.repoResponse = data

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        showDataObserver.assertValueCount(1)
        vm.data.shouldEqual(data)
    }

    @Test
    fun getRepoErrorShouldShowHttpError() {
        gitHubClient.error = HttpErrors.getHttpException(404)

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(HttpErrors.DEFAULT_HTTP_ERROR_MESSAGE)
    }

    @Test
    fun getRepoErrorShouldShowDefaultError() {
        gitHubClient.error = Exception("Failed")

        vm.getRepo("any", "thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(R.string.default_error_message)
    }

}