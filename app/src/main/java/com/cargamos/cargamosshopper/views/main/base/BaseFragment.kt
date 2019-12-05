package com.cargamos.cargamosshopper.views.main.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cargamos.cargamosshopper.extension.vm
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.reflect.KClass

open class BaseFragment<T: ViewModel>(model: KClass<T>) : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel by lazy { vm(viewModelFactory, model) }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}