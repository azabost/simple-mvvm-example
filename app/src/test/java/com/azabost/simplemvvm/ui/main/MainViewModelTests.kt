package com.azabost.simplemvvm.ui.main

import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.net.ApiClient
import com.azabost.simplemvvm.net.GitHubService
import com.azabost.simplemvvm.net.response.RepoResponse
import com.azabost.simplemvvm.utils.HttpErrors
import com.azabost.simplemvvm.utils.MockitoUtils
import com.azabost.simplemvvm.utils.getHttpException
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.mockito.Mockito
import pl.brightinventions.myparkinson.net.connectivity.FakeConnectivity

class MainViewModelTests {

    private val gitHubService = Mockito.mock(GitHubService::class.java)
    private val connectivity = FakeConnectivity()
    private val apiClient = ApiClient(gitHubService, connectivity)
    private val testScheduler = TestScheduler()
    private val vm = MainViewModel(apiClient)

    private val progressObserver = TestObserver.create<Boolean>().apply {
        vm.progress.subscribe(this)
    }
    private val errorObserver = TestObserver.create<Int>().apply {
        vm.errors.subscribe(this)
    }
    private val showDataObserver = TestObserver.create<RepoResponse>().apply {
        vm.data.subscribe(this)
    }

    @Test
    fun getRepoShouldShowProgress() {
        val data = RepoResponse(12345)
        mockGetRepoResponse(data)

        vm.getRepoData("any/thing")
        progressObserver.assertValue(true)

        testScheduler.triggerActions()
        progressObserver.assertValueSequence(listOf(true, false))
    }

    @Test
    fun getRepoShouldNotShowError() {
        val data = RepoResponse(12345)
        mockGetRepoResponse(data)

        vm.getRepoData("any/thing")
        testScheduler.triggerActions()

        errorObserver.assertEmpty()
    }

    @Test
    fun getRepoShouldShowData() {
        val data = RepoResponse(12345)
        mockGetRepoResponse(data)

        vm.getRepoData("any/thing")
        testScheduler.triggerActions()

        showDataObserver.assertValue(data)
    }

    @Test
    fun getRepoErrorShouldShowHttpError() {
        mockGetRepoResponse(error = HttpErrors.getHttpException(401))

        vm.getRepoData("any/thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(HttpErrors.DEFAULT_HTTP_ERROR_MESSAGE)
    }

    @Test
    fun getRepoErrorShouldShowHttp404Error() {
        mockGetRepoResponse(error = HttpErrors.getHttpException(404))

        vm.getRepoData("any/thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(R.string.no_such_repo)
    }

    @Test
    fun getRepoErrorShouldShowDefaultError() {
        mockGetRepoResponse(error = Exception("Failed"))

        vm.getRepoData("any/thing")
        testScheduler.triggerActions()

        errorObserver.assertValue(R.string.default_error_message)
    }

    @Test
    fun shouldShowParsingErrorIfWrongSplits() {
        vm.getRepoData("anything")
        val error1 = R.string.wrong_repo_format

        vm.getRepoData("any/thing/")
        val error2 = R.string.wrong_repo_format

        val errors = arrayOf(error1, error2)
        errorObserver.assertValues(*errors)
    }

    @Test
    fun shouldShowNoInternetConnectionError() {
        connectivity.isConnected = false

        vm.getRepoData("any/thing")

        errorObserver.assertValue(R.string.error_no_internet_connection)
    }

    private fun mockGetRepoResponse(
        response: RepoResponse? = null,
        error: Throwable? = null
    ) {
        MockitoUtils.mockApiResponse(
            Mockito.`when`(gitHubService.getRepo(Mockito.anyString(), Mockito.anyString())),
            testScheduler,
            response,
            error
        )
    }
}