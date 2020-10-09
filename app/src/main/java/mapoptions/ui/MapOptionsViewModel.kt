package mapoptions.ui

import base.routing.Router
import base.routing.RoutingActionSender
import base.ui.BaseViewModel
import io.reactivex.Scheduler
import mapoptions.model.MapOptions
import mapoptions.usecase.GetMapOptions
import mapoptions.usecase.UpdateMapOptions

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
