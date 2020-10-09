package com.markosopcic.lostandfound.map.ui

import base.routing.Router
import base.routing.RoutingActionSender
import base.ui.BaseViewModel
import com.markosopcic.lostandfound.locationlib.model.Coordinates
import com.markosopcic.lostandfound.locationlib.usecase.GetCurrentLocation
import com.markosopcic.lostandfound.locationlib.usecase.GetLocationPermissionStatus
import com.markosopcic.lostandfound.locationlib.usecase.RequestLocationPermission
import com.markosopcic.lostandfound.map.ui.MapViewState.LocationPermissionStatus
import com.markosopcic.lostandfound.reporteditemslib.usecase.CenterAndRange
import com.markosopcic.lostandfound.reporteditemslib.usecase.GetNearReportedItems
import com.markosopcic.lostandfound.reporteditemslib.usecase.UpdateCurrentMapCenter
import io.reactivex.Scheduler
import io.reactivex.processors.BehaviorProcessor

class MapViewModel(
        private val getLocationPermissionStatus: GetLocationPermissionStatus,
        private val requestLocationPermission: RequestLocationPermission,
        private val getNearReportedItems: GetNearReportedItems,
        private val getCurrentLocation: GetCurrentLocation,
        private val updateCurrentMapCenter: UpdateCurrentMapCenter,
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        routingActionSender: RoutingActionSender
) : BaseViewModel<MapViewState>(backgroundScheduler, mainScheduler, routingActionSender) {

    private val mapInitializedProcessor = BehaviorProcessor.createDefault(false)

    private val shownItemIds = mutableSetOf<Int>()

    init {
        observe(getCurrentLocation().map {
            MapViewState.LocationViewState(
                    it.longitude,
                    it.latitude
            )
        })
        execute(mapInitializedProcessor
                .filter { it }
                .switchMapCompletable {
                    getLocationPermissionStatus()
                            .filter { !it }
                            .switchMapCompletable(::requestPermission)
                })
        observe(mapInitializedProcessor
                .filter { it }
                .switchMap { getLocationPermissionStatus().map(::LocationPermissionStatus) })

        observe(getNearReportedItems()
                .map { it.filter { !shownItemIds.contains(it.id) } }
                .doOnNext { it.forEach { shownItemIds.add(it.id) } }
                .map { MapViewState.NearReportedItems(it) })
    }

    fun mapInitialized() {
        mapInitializedProcessor.onNext(true)
    }

    fun mapRecentered(center: Coordinates, widthMeters: Double) {
        execute(
            updateCurrentMapCenter(
                CenterAndRange(
                    center.longitude,
                    center.latitude,
                    widthMeters
                )
            )
        )
    }

    private fun requestPermission(permission: Boolean) = requestLocationPermission()

    fun markerClicked(id: Int) {

    }

    fun showOptions() {
        sendRoutingAction(Router::showMapOptionsScreen)
    }
}
