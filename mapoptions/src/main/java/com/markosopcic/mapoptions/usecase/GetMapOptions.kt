package com.markosopcic.mapoptions.usecase

import com.markosopcic.core.usecase.QueryUseCase
import com.markosopcic.mapoptions.model.MapOptions
import com.markosopcic.mapoptions.source.MapOptionsSource
import io.reactivex.Flowable

class GetMapOptions(private val mapOptionsSource: MapOptionsSource) : QueryUseCase<MapOptions> {

    override fun invoke(): Flowable<MapOptions> = mapOptionsSource.getMapOptions()
        .distinctUntilChanged()
}
