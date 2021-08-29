package com.mine.mvvmmitch.di.ui

import com.mine.mvvmmitch.auth.ui.ForgotPasswordFragment
import com.mine.mvvmmitch.auth.ui.LauncherFragment
import com.mine.mvvmmitch.auth.ui.LoginFragment
import com.mine.mvvmmitch.auth.ui.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
// it provide objects to object graph
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    //This annotation must be applied to an abstract method in a Module that returns a concrete Android framework type
    // (e.g. FooActivity, BarFragment, MyService, etc).
    // The method should have no parameters.
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment

}