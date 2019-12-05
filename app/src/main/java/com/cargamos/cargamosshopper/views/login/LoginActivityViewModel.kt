package com.cargamos.cargamosshopper.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cargamos.cargamosshopper.api.LoginService
import com.cargamos.cargamosshopper.api.ShopperService
import com.google.firebase.auth.FirebaseAuth
import com.pixplicity.easyprefs.library.Prefs
import javax.inject.Inject

class LoginActivityViewModel @Inject
constructor(
    private val loginService: LoginService,
    private val shopperService: ShopperService
) : ViewModel() {
    val batchCommitReady: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { setValue(null) }
    }

    fun getShopperProperties() {
        FirebaseAuth.getInstance().currentUser?.let{
            shopperService.getShopper(it.uid)
                .get()
                .addOnSuccessListener {
                    val document = it.documents[0]

                    val darkstore = document["darkstore_uid"] as String
                    val shopperName = document["name"] as String

                    Prefs.putString("darkstore_uid", darkstore)
                    Prefs.putString("shopper_name", shopperName)

                    batchCommitReady.postValue(true)
                }
        }
    }
}