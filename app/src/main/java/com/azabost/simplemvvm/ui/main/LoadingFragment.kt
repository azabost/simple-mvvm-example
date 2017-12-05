package com.azabost.simplemvvm.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_loading.*
import javax.inject.Inject

class LoadingFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var vm: LoadingVM

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_loading, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm = ViewModelProviders.of(activity, vmFactory).get(MainViewModel::class.java)

        getRepoButton.setOnClickListener {
            vm.getRepo("azabost", "android-sdk")
        }
    }
}