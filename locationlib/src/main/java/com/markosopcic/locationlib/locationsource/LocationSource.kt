package com.markosopcic.locationlib.locationsource

import com.markosopcic.locationlib.model.Coordinates
import io.reactivex.Completable
import io.reactivex.Flowable

interface LocationSource {

    fun locationPermissionGranted(): Flowable<Boolean>

    fun getCurrentLocation(): Flowable<Coordinates>

    fun requestLocationPermission(): Completable
}
