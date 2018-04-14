package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.ui.BaseActivity
import com.azabost.simplemvvm.utils.hide
import com.azabost.simplemvvm.utils.logger
import com.azabost.simplemvvm.utils.observeOnMainThread
import com.azabost.simplemvvm.utils.show
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var vm: MainVM

    private val log = logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)

        showLoadingFragment()

        vm.progress.bindToLifecycle(this).observeOnMainThread().subscribe({
            if (it) {
                log.debug("Showing progress")
                showProgress()
            } else {
                log.debug("Hiding progress")
                hideProgress()
            }
        }, {
            log.error("Error observing progress", it)
        })

        vm.showData.bindToLifecycle(this).observeOnMainThread().subscribe({
            showDataFragment()
        }, {
            log.error("Error observing repo response", it)
        })

        vm.errors.bindToLifecycle(this).observeOnMainThread().subscribe({
            showError(it)
        }, {
            log.error("Error observing error messages", it)
        })
    }

    private fun showError(errorRes: Int) {
        Toast.makeText(this, errorRes, Toast.LENGTH_LONG).show()
    }

    private fun showLoadingFragment() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, LoadingFragment())
                    .commit()
        }
    }

    private fun showDataFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, DataFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun showProgress() {
        progress.show()
    }

    private fun hideProgress() {
        progress.hide()
    }
}
