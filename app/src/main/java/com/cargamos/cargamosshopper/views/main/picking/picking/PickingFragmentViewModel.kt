package com.cargamos.cargamosshopper.views.main.picking.picking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cargamos.cargamosshopper.api.Api
import com.cargamos.cargamosshopper.api.PickingService
import com.cargamos.cargamosshopper.api.PickingServiceInterface
import com.cargamos.cargamosshopper.models.Order
import com.cargamos.cargamosshopper.models.Picking
import com.cargamos.cargamosshopper.models.Product
import com.cargamos.cargamosshopper.utils.PrefsKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PickingFragmentViewModel @Inject
constructor(
    private val pickingService: PickingService,
    private val pickingServiceInterface: PickingServiceInterface
) : ViewModel() {
    var pickingListenerRegistration: ListenerRegistration? = null

    val pickingItems: MutableLiveData<ArrayList<Picking>> by lazy {
        MutableLiveData<ArrayList<Picking>>().apply { setValue(null) }
    }

    val batchCommitReady: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { setValue(null) }
    }

    fun observePickingItems() {
        pickingListenerRegistration =
            pickingService.getPickingAssignedToShopper()
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    querySnapshot?.let { query ->
                        val list = arrayListOf<Picking>()

                        FirebaseAuth.getInstance().currentUser?.let { user ->
                            for (document in query.documents) {
                                document.toObject(Picking::class.java)?.let { picking ->
                                    picking.state?.let {
                                        if (it != "AWAITING_SOLVING") {
                                            list.add(picking)
                                        }
                                    } ?: run {
                                        list.add(picking)
                                    }

                                }
                            }
                        }

                        pickingItems.postValue(list)
                    }
                }
    }


    fun pickItem(picking: Picking, decrementCount: Int) {
        lateinit var datestamp: String

        if (Api.test) {
            datestamp = "TEST"
        } else {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = Date()
            datestamp = dateFormat.format(date)
        }


        var call: Call<Any> =
            pickingServiceInterface.pickProduct(
                Prefs.getString(PrefsKeys.darkstoreName, ""),
                datestamp,
                picking.domain,
                picking.id,
                decrementCount.toString()
            )


        call.enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Timber.d(response.message())
                    batchCommitReady.postValue(true)
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Timber.d(t.localizedMessage)
                }

            }
        )
    }
}