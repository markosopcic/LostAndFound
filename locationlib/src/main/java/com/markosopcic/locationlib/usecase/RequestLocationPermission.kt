package com.markosopcic.locationlib.usecase

import com.markosopcic.core.usecase.CompletableUseCase
import com.markosopcic.locationlib.locationsource.LocationSource
import io.reactivex.Completable

class RequestLocationPermission(private val locationSource: LocationSource) : CompletableUseCase {

    override fun invoke(): Completable = locationSource.requestLocationPermission()
}
