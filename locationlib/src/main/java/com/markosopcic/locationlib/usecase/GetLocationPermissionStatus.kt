package com.markosopcic.locationlib.usecase

import com.markosopcic.core.usecase.QueryUseCase
import com.markosopcic.locationlib.locationsource.LocationSource
import io.reactivex.Flowable

class GetLocationPermissionStatus(locationSource: LocationSource) : QueryUseCase<Boolean> {

    private val locationPermissionStatus = locationSource.locationPermissionGranted().distinctUntilChanged()

    override fun invoke(): Flowable<Boolean> = locationPermissionStatus
}
