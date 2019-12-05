package com.cargamos.cargamosshopper.models

data class Order(
    var code: String = "",
    var created: Long = 0,
    var domain: String = "",
    var id: String = "",
    var products: ArrayList<Product> = arrayListOf(),
    var service_id: String = "",
    var state: String = "",
    var updated: Long = 0,
    var value: String = "",
    var group: Long = 0,
    var label_url: String = "",
    var items: ArrayList<Map<String, Any>> = arrayListOf(),
    var order_id: String = "",
    var subgroup: Long = 0,
    var driver_uid: String? = null,
    var driver_name: String? = null,
    var evidences: ArrayList<Map<String, Any>> = arrayListOf(),
    var show_picking: Boolean = true,
    var shopper_uid: String? = null,
    var operation_ref: String? = null,
    var foreign_operation_ref: String? = null,
    var darkstore_uid: String? = null,
    var last_shopper_uid: String? = null,
    //Used for recyclerview
    var printed: Boolean = false,
    var scanned: Boolean = false,
    var route_scanned: Boolean = false,
    var manualRoutingSelected: Boolean = false
)