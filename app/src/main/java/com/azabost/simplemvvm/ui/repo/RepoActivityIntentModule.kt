package com.azabost.simplemvvm.ui.repo

import com.azabost.simplemvvm.net.response.RepoResponse
import dagger.Module
import dagger.Provides

@Module
class RepoActivityIntentModule {
    @Provides
    fun providesRepoResponse(activity: RepoActivity): RepoResponse {
        return activity.intent.getSerializableExtra(RepoActivity.REPO_RESPONSE_EXTRA) as RepoResponse
    }
}