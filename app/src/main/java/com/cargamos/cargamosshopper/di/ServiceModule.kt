package com.cargamos.cargamosshopper.di

import com.cargamos.cargamosshopper.services.MainService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ServiceModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainService(): MainService
}