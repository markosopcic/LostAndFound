package com.markosopcic.mapoptions.ui

import com.markosopcic.core.routing.Router
import com.markosopcic.core.routing.RoutingActionSender
import com.markosopcic.core.ui.BaseViewModel
import com.markosopcic.mapoptions.model.MapOptions
import com.markosopcic.mapoptions.usecase.GetMapOptions
import com.markosopcic.mapoptions.usecase.UpdateMapOptions
import io.reactivex.Scheduler

class MapOptionsViewModel(
    getMapOptions: GetMapOptions,
    private val updateMapOptions: UpdateMapOptions,
    backgroundScheduler: Scheduler,
    mainScheduler: Scheduler,
    routingActionSender: RoutingActionSender
) : BaseViewModel<MapOptionsViewState>(backgroundScheduler, mainScheduler, routingActionSender) {

    init {
        observe(getMapOptions().map(::mapToViewState))
    }

    fun closeMapOptions() {
        sendRoutingAction(Router::closeMapOptions)
    }

    fun updateMapOptions(radiusMeters: Long, daysFrom: Long, daysTo: Long) {
        execute(updateMapOptions(MapOptions(radiusMeters, daysFrom, daysTo)))
    }

    private fun mapToViewState(options: MapOptions) = with(options) {
        MapOptionsViewState.Options(radiusMeters, minDaysAgo, maxDaysAgo)
    }

}
