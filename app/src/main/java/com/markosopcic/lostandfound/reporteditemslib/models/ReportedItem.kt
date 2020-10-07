package com.markosopcic.lostandfound.reporteditemslib.models

data class ReportedItem(
    val contactNumber: String,
    val description: String,
    val found: Boolean,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val lost: Boolean,
    val type: String
)
