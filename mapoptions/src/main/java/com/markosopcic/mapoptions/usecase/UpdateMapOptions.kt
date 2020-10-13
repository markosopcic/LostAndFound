package com.markosopcic.mapoptions.usecase

import com.markosopcic.core.usecase.CompletableUseCaseWithParam
import com.markosopcic.mapoptions.model.MapOptions
import com.markosopcic.mapoptions.source.MapOptionsSource
import io.reactivex.Completable

class UpdateMapOptions(private val mapOptionsSource: MapOptionsSource) : CompletableUseCaseWithParam<MapOptions> {

    override fun invoke(param: MapOptions): Completable = mapOptionsSource.setMapOptions(param)
}
