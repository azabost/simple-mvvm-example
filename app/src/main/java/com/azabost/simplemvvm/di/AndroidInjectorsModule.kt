package com.azabost.simplemvvm.di

import com.azabost.simplemvvm.ui.main.MainActivity
import com.azabost.simplemvvm.ui.repo.RepoActivity
import com.azabost.simplemvvm.ui.repo.RepoActivityIntentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [RepoActivityIntentModule::class])
    abstract fun contributeRepoActivity(): RepoActivity
}