package com.mine.mvvmmitch.di.ui

import com.mine.mvvmmitch.auth.AuthActivity
import com.mine.mvvmmitch.di.ui.auth.AuthModule
import com.mine.mvvmmitch.di.ui.auth.AuthScope
import com.mine.mvvmmitch.di.ui.auth.AuthViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

}