package com.markosopcic.reporteditemslib.models

import com.squareup.moshi.Json

data class APIReportedItem(
    @Json(name = "contactNumber")
    val contactNumber: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "found")
    val found: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "lost")
    val lost: Boolean,
    @Json(name = "type")
    val type: String
)
