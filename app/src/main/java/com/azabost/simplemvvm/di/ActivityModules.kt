package com.azabost.simplemvvm.di

import com.azabost.simplemvvm.ui.main.DataFragment
import com.azabost.simplemvvm.ui.main.LoadingFragment
import com.azabost.simplemvvm.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModules {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoadingFragment(): LoadingFragment

    @ContributesAndroidInjector
    abstract fun contributeDataFragment(): DataFragment
}