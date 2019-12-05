package com.cargamos.cargamosshopper.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cargamos.cargamosdarkstore.factory.AppViewModelFactory
import com.cargamos.cargamosshopper.views.login.LoginActivityViewModel
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.cargamos.cargamosshopper.views.main.MainActivityViewModel
import com.cargamos.cargamosshopper.views.main.picking.picking.PickingFragmentViewModel
import com.cargamos.cargamosshopper.views.main.picking.pool.PickingPoolFragment
import com.cargamos.cargamosshopper.views.main.picking.pool.PickingPoolFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    internal abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PickingFragmentViewModel::class)
    internal abstract fun bindPickingFragmentViewModel(viewModel: PickingFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PickingPoolFragmentViewModel::class)
    internal abstract fun bindPickingPoolFragmentViewModel(viewModel: PickingPoolFragmentViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}