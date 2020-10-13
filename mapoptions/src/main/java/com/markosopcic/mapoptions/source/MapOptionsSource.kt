package com.markosopcic.mapoptions.source

import com.markosopcic.mapoptions.model.MapOptions
import io.reactivex.Completable
import io.reactivex.Flowable

interface MapOptionsSource {

    fun getMapOptions(): Flowable<MapOptions>

    fun setMapOptions(options: MapOptions): Completable
}
