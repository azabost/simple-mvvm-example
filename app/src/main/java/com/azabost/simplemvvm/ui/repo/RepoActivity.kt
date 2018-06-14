package com.azabost.simplemvvm.ui.repo

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.di.ViewModelFactory
import com.azabost.simplemvvm.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_repo.*
import javax.inject.Inject

class RepoActivity : BaseActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<RepoViewModel>

    lateinit var vm: RepoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        vm = ViewModelProviders.of(this, vmFactory)[RepoViewModel::class.java]

        repoData.text = vm.repoResponse.id.toString()
    }

    companion object {
        const val REPO_RESPONSE_EXTRA = "REPO_RESPONSE_EXTRA"
    }
}