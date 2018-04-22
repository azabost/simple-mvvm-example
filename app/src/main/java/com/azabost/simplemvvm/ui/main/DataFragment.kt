package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.ui.BaseFragment
import com.azabost.simplemvvm.utils.logger
import com.azabost.simplemvvm.utils.observeOnMainThread
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_data.*
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

        val adapter = DataAdapter()

        dataRecycler.adapter = adapter
        dataRecycler.layoutManager = LinearLayoutManager(activity)

        vm.data.bindToLifecycle(this).observeOnMainThread().subscribe({
            log.info("Setting new data")
            adapter.setItems(it)
        })
    }
}
