package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.di.ViewModelFactory
import com.azabost.simplemvvm.ui.BaseActivity
import com.azabost.simplemvvm.ui.repo.RepoActivity
import com.azabost.simplemvvm.utils.hide
import com.azabost.simplemvvm.utils.observeOnMainThread
import com.azabost.simplemvvm.utils.show
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<MainViewModel>

    lateinit var vm: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProviders.of(this, vmFactory)[MainViewModel::class.java]

        vm.progress.bindToLifecycle(this).observeOnMainThread().subscribe {
            if (it) progress.show() else progress.hide()
        }

        vm.errors.bindToLifecycle(this).observeOnMainThread().subscribe {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        vm.data.bindToLifecycle(this).observeOnMainThread().subscribe {
            val intent = Intent(this, RepoActivity::class.java).apply {
                putExtra(RepoActivity.REPO_RESPONSE_EXTRA, it)
            }
            startActivity(intent)
        }

        getRepoDataButton.setOnClickListener {
            vm.getRepoData(repoNameInput.text.toString())
        }
    }
}