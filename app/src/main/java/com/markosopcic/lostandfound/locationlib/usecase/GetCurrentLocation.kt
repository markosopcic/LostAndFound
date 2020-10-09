package com.markosopcic.lostandfound.locationlib.usecase

import base.usecase.QueryUseCase
import com.markosopcic.lostandfound.locationlib.locationsource.LocationSource
import com.markosopcic.lostandfound.locationlib.model.Coordinates
import io.reactivex.Flowable

class GetCurrentLocation(private val locationSource: LocationSource) : QueryUseCase<Coordinates> {

    override fun invoke(): Flowable<Coordinates> = locationSource.getCurrentLocation()
}
