package com.cargamos.cargamosshopper.views.main.picking.pool

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cargamos.cargamosshopper.api.PickingService
import com.cargamos.cargamosshopper.models.Order
import com.cargamos.cargamosshopper.models.Picking
import com.cargamos.cargamosshopper.models.Product
import com.cargamos.cargamosshopper.utils.PrefsKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.pixplicity.easyprefs.library.Prefs
import javax.inject.Inject

class PickingPoolFragmentViewModel @Inject
constructor(
    private val pickingService: PickingService
) : ViewModel() {
    var poolListenerRegistration: ListenerRegistration? = null

    val poolItems: MutableLiveData<ArrayList<Picking>> by lazy {
        MutableLiveData<ArrayList<Picking>>().apply { setValue(null) }
    }

    val batchCommitReady: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { setValue(null) }
    }

    fun getPickingItems() {
        poolListenerRegistration =
            pickingService.getPickingFromToday()
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    querySnapshot?.let {
                        val pickings = arrayListOf<Picking>()

                        for (document in it.documents) {
                            document.toObject(Picking::class.java)?.let {
                                if (it.state != "AWAITING_SOLVING") {
                                    pickings.add(it)
                                }
                            }
                        }

                        poolItems.postValue(pickings)
                    }
                }
    }

    fun takePicking(id: String) {
        lateinit var uid: String

        FirebaseAuth.getInstance().currentUser?.let { user ->
            uid = user.uid
        }

        val map = hashMapOf<String, Any>(
            Pair("shopper_uid", uid),
            Pair("shopper_name", Prefs.getString(PrefsKeys.shopperName, ""))
        )

        pickingService.getPicking(id).update(map)
            .addOnSuccessListener {
                batchCommitReady.postValue(true)
            }
    }

    fun takeAllPickings(ids: ArrayList<String>) {
        val batch = pickingService.database.batch()

        lateinit var uid: String

        FirebaseAuth.getInstance().currentUser?.let { user ->
            uid = user.uid
        }

        val map = hashMapOf<String, Any>(
            Pair("shopper_uid", uid),
            Pair("shopper_name", Prefs.getString(PrefsKeys.shopperName, ""))
        )

        for (item in ids) {
            batch.update(pickingService.getPicking(item), map)
        }

        batch.commit().addOnSuccessListener {
            batchCommitReady.postValue(true)
        }
    }
}