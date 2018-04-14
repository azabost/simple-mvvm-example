package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.ui.BaseFragment
import com.azabost.simplemvvm.utils.logger
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import javax.inject.Inject

class DataFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var vm: DataVM

    private val log = logger

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_data, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm = ViewModelProviders.of(activity!!, vmFactory).get(MainViewModel::class.java)

        vm.data.bindToLifecycle(this).subscribe({
            it.forEach {
                log.info(it.toString())
            }
        })
    }
}