package mapoptions.source

import io.reactivex.Completable
import io.reactivex.Flowable
import mapoptions.model.MapOptions

interface MapOptionsSource {

    fun getMapOptions(): Flowable<MapOptions>

    fun setMapOptions(options: MapOptions): Completable
}
