package com.cargamos.cargamosshopper.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Product(
    var created: Long = 0,
    var updated: Long = 0,
    var description: String = "",
    var enabled: Boolean = true,
    var id: String = "",
    var available: Long = 0,
    var quantity: Long = 0,
    var sorting: String = "",
    //variables used for recyclerview,
    //PACKING
    var scanned: Boolean = false,
    var orderID: String = "",
    //PICKING
    var picked: Boolean = false
)