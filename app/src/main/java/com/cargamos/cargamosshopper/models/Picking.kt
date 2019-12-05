package com.cargamos.cargamosshopper.models

import com.cargamos.cargamosshopper.extension.DynamicSearchAdapter

data class Picking (
    val id: String = "",
    val orders: ArrayList<String> = arrayListOf(),
    val description: String = "",
    val location: String = "",
    var quantity: Long = 0,
    var state: String? = null,
    var count: Long = 0,
    var domain: String = "",
    var enabled: Boolean = true,
    var name: String = "",
    var packings: ArrayList<String> = arrayListOf(),
    var shopper_uid: String? = null,
    var sorting: String? = null,
    var updated: Long = 0,
    var show: Boolean = false
) : DynamicSearchAdapter.Searchable {
    override fun getSearchCriteria(): String {
        return sorting!!
    }
}