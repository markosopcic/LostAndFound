package com.markosopcic.map.ui

import com.markosopcic.reporteditemslib.models.ReportedItem

sealed class MapViewState {

    class LocationViewState(val longitude: Double, val latitude: Double) : MapViewState()
    class LocationPermissionStatus(val hasPermission: Boolean) : MapViewState()
    class NearReportedItems(val items: List<ReportedItem>) : MapViewState()
}

