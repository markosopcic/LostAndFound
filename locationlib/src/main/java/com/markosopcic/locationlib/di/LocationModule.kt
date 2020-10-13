package com.markosopcic.locationlib.di

import com.markosopcic.locationlib.locationsource.LocationSource
import com.markosopcic.locationlib.locationsource.LocationSourceImpl
import com.markosopcic.locationlib.usecase.GetCurrentLocation
import com.markosopcic.locationlib.usecase.GetLocationPermissionStatus
import com.markosopcic.locationlib.usecase.RequestLocationPermission
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun locationModule() = module {

    single<LocationSource> { LocationSourceImpl(androidApplication(), get()) }

    single { GetLocationPermissionStatus(get()) }

    single { RequestLocationPermission(get()) }

    single { GetCurrentLocation(get()) }
}
