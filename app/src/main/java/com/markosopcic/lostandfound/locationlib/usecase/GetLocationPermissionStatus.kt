package com.markosopcic.lostandfound.locationlib.usecase

import base.usecase.QueryUseCase
import com.markosopcic.lostandfound.locationlib.locationsource.LocationSource
import io.reactivex.Flowable

class GetLocationPermissionStatus(locationSource: LocationSource) : QueryUseCase<Boolean> {

    private val locationPermissionStatus = locationSource.locationPermissionGranted().distinctUntilChanged()

    override fun invoke(): Flowable<Boolean> = locationPermissionStatus
}
