package com.azabost.simplemvvm.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.azabost.simplemvvm.di.ViewModelFactory
import dagger.android.AndroidInjection


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T : ViewModel> ViewModelFactory<T>.get(): T =
        ViewModelProviders.of(this@BaseActivity, this)[T::class.java]
}
