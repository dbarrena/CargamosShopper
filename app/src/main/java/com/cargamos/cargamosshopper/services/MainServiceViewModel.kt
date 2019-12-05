package com.cargamos.cargamosshopper.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cargamos.cargamosshopper.api.PickingService
import com.cargamos.cargamosshopper.models.Order
import com.cargamos.cargamosshopper.models.Picking
import com.cargamos.cargamosshopper.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

class MainServiceViewModel @Inject
constructor(
    private val pickingService: PickingService
) : ViewModel() {
    var poolListenerRegistration: ListenerRegistration? = null

    val poolItems: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply { setValue(null) }
    }

    var newPickingsLastValue = 0

    var firstTimeLoading = true

    fun getPickingItems() {
        poolListenerRegistration =
            pickingService.getPickingFromToday()
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    querySnapshot?.let {
                        if (!it.metadata.isFromCache) {
                            poolItems.postValue(it.documents.size)
                        }
                    }
                }
    }
}