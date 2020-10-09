package mapoptions.usecase

import base.usecase.CompletableUseCaseWithParam
import io.reactivex.Completable
import mapoptions.model.MapOptions
import mapoptions.source.MapOptionsSource

class UpdateMapOptions(private val mapOptionsSource: MapOptionsSource) : CompletableUseCaseWithParam<MapOptions> {

    override fun invoke(param: MapOptions): Completable = mapOptionsSource.setMapOptions(param)
}
