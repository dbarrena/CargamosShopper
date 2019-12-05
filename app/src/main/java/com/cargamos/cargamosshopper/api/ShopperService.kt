package com.cargamos.cargamosshopper.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pixplicity.easyprefs.library.Prefs

class ShopperService {
    val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getShopper(uid: String): Query {
        return database.collection(Api.DATABASE_NAME)
            .document("darkstores-shoppers")
            .collection("DEFAULT")
            .whereEqualTo("uid", uid)
    }
}