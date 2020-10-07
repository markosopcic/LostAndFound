package com.markosopcic.lostandfound.locationlib.usecase

import base.usecase.CompletableUseCase
import com.markosopcic.lostandfound.locationlib.locationsource.LocationSource
import io.reactivex.Completable

class RequestLocationPermission(private val locationSource: LocationSource) : CompletableUseCase {

    override fun invoke(): Completable = locationSource.requestLocationPermission()
}
