package com.cargamos.cargamosshopper.di

import com.cargamos.cargamosshopper.views.main.picking.picking.PickingFragment
import com.cargamos.cargamosshopper.views.main.picking.pool.PickingPoolFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class PickingFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributePickingFragment(): PickingFragment

    @ContributesAndroidInjector
    abstract fun contributePickingPoolFragment(): PickingPoolFragment
}