package com.cargamos.cargamosshopper.views.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(): ViewModel(){
    var activeFragment: MutableLiveData<Fragment> = MutableLiveData()
    var firstTimeLoading = true
}