package mapoptions.usecase

import base.usecase.QueryUseCase
import io.reactivex.Flowable
import mapoptions.model.MapOptions
import mapoptions.source.MapOptionsSource

class GetMapOptions(private val mapOptionsSource: MapOptionsSource) : QueryUseCase<MapOptions> {

    override fun invoke(): Flowable<MapOptions> = mapOptionsSource.getMapOptions()
        .distinctUntilChanged()
}
