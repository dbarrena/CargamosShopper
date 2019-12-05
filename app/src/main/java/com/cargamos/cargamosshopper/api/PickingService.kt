package com.cargamos.cargamosshopper.api

import com.cargamos.cargamosshopper.api.Api.test
import com.cargamos.cargamosshopper.utils.PrefsKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pixplicity.easyprefs.library.Prefs
import java.text.SimpleDateFormat
import java.util.*

class PickingService {
    val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getPickingFromToday(): Query {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()

        val ref = database.collection(Api.DATABASE_NAME)
            .document("darkstores")
            .collection("profiles")
            .document(Prefs.getString(PrefsKeys.darkstoreName, "test"))
            .collection("pickings")

        return if (test) {
            ref.document("TEST")
                .collection("DEFAULT")
                .whereEqualTo("shopper_uid", null)
        } else {
            ref.document(dateFormat.format(date))
                .collection("DEFAULT")
                .whereEqualTo("shopper_uid", null)
        }
    }

    fun getPickingAssignedToShopper(): Query {
        lateinit var uid: String

        FirebaseAuth.getInstance().currentUser?.let {
            uid = it.uid
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()

        val ref = database.collection(Api.DATABASE_NAME)
            .document("darkstores")
            .collection("profiles")
            .document(Prefs.getString(PrefsKeys.darkstoreName, null))
            .collection("pickings")

        return if (test) {
            ref.document("TEST")
                .collection("DEFAULT")
                .whereEqualTo("shopper_uid", uid)
        } else {
            ref.document(dateFormat.format(date))
                .collection("DEFAULT")
                .whereEqualTo("shopper_uid", uid)
        }
    }

    fun getPicking(id: String): DocumentReference {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()

        val ref = database.collection(Api.DATABASE_NAME)
            .document("darkstores")
            .collection("profiles")
            .document(Prefs.getString(PrefsKeys.darkstoreName, null))
            .collection("pickings")

        return if (test) {
            ref.document("TEST")
                .collection("DEFAULT")
                .document(id)
        } else {
            ref.document(dateFormat.format(date))
                .collection("DEFAULT")
                .document(id)
        }
    }

    fun getOrdersFromToday(): CollectionReference {
        return database.collection(Api.DATABASE_NAME)
            .document("darkstores")
            .collection("profiles")
            .document(Prefs.getString("darkstore_uid", null))
            .collection("packings")
            .document("TEST")
            .collection("ifonda.com")
    }
}