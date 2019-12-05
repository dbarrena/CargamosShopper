package com.cargamos.cargamosshopper.di

import com.cargamos.cargamosshopper.views.login.LoginActivity
import com.cargamos.cargamosshopper.views.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(
        modules = [
            PickingFragmentModule::class
        ]
    )
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity
}