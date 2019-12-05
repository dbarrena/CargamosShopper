package com.cargamos.cargamosshopper.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PickingServiceInterface {
    @GET("https://crts-orders-staging-dot-cargamos-215500.appspot.com/pickings/DECREMENT_PICKING/{darkstore_name}/{date}/{domain}/{picking_id}/{count}")
    fun pickProduct(
        @Path("darkstore_name") darkstoreName: String,
        @Path("date") date: String,
        @Path("domain") domain: String,
        @Path("picking_id") pickingID: String,
        @Path("count") count: String
    ): Call<Any>
}