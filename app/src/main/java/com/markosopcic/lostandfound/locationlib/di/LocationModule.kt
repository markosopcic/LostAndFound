package com.markosopcic.lostandfound.locationlib.di

import com.markosopcic.lostandfound.locationlib.locationsource.LocationSource
import com.markosopcic.lostandfound.locationlib.locationsource.LocationSourceImpl
import com.markosopcic.lostandfound.locationlib.usecase.GetCurrentLocation
import com.markosopcic.lostandfound.locationlib.usecase.GetLocationPermissionStatus
import com.markosopcic.lostandfound.locationlib.usecase.RequestLocationPermission
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun locationModule() = module {

    single<LocationSource> { LocationSourceImpl(androidApplication(), get()) }

    single { GetLocationPermissionStatus(get()) }

    single { RequestLocationPermission(get()) }

    single { GetCurrentLocation(get()) }
}
