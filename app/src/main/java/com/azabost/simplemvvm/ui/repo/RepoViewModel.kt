package com.azabost.simplemvvm.ui.repo

import android.arch.lifecycle.ViewModel
import com.azabost.simplemvvm.net.response.RepoResponse
import javax.inject.Inject

interface RepoVM {
    val repoResponse: RepoResponse
}

class RepoViewModel @Inject constructor(
    override val repoResponse: RepoResponse
) : ViewModel(), RepoVM