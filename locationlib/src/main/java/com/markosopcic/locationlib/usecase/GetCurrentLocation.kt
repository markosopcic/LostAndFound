package com.markosopcic.locationlib.usecase

import com.markosopcic.core.usecase.QueryUseCase
import com.markosopcic.locationlib.locationsource.LocationSource
import com.markosopcic.locationlib.model.Coordinates
import io.reactivex.Flowable

class GetCurrentLocation(private val locationSource: LocationSource) : QueryUseCase<Coordinates> {

    override fun invoke(): Flowable<Coordinates> = locationSource.getCurrentLocation()
}
