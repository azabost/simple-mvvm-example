package com.azabost.simplemvvm.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.azabost.simplemvvm.di.ViewModelFactory
import com.azabost.simplemvvm.utils.HasLifecycleScopeProvider
import com.uber.autodispose.LifecycleScopeProvider
import com.uber.autodispose.android.lifecycle.scope
import dagger.android.AndroidInjection


abstract class BaseActivity : AppCompatActivity(), HasLifecycleScopeProvider {

    override val scopeProvider: LifecycleScopeProvider<*> by lazy { scope() }
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T : ViewModel> ViewModelFactory<T>.get(): T =
        ViewModelProviders.of(this@BaseActivity, this)[T::class.java]
}
