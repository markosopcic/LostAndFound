package com.markosopcic.lostandfound.locationlib.locationsource

import android.Manifest
import android.app.Application
import android.app.Service
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import base.routing.RoutingActionSender
import com.markosopcic.lostandfound.locationlib.model.Coordinates
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

private const val LOCATION_REFRESH_INTERVAL = 0L
private const val LOCATION_REFRESH_DISTANCE = 0F
private const val CONSUME_LOCATIONS = 1

class LocationSourceImpl(private val application: Application, private val routingActionSender: RoutingActionSender) : LocationSource,
    ActivityCompat.OnRequestPermissionsResultCallback {

    init {
        startListeningToLocationChanges()
    }

    private val locationProcessor = BehaviorProcessor.create<Coordinates>()

    private val permissionGrantedProcessor =
            BehaviorProcessor.createDefault(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))

    override fun locationPermissionGranted(): Flowable<Boolean> = permissionGrantedProcessor

    override fun getCurrentLocation(): Flowable<Coordinates> = locationProcessor

    override fun requestLocationPermission(): Completable =
        Completable.fromAction {
            routingActionSender.sendRoutingAction {
                it.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions.any { it == Manifest.permission.ACCESS_FINE_LOCATION }) {
            val isLocationGranted =
                    grantResults[permissions.indexOf(Manifest.permission.ACCESS_FINE_LOCATION)] == PERMISSION_GRANTED
            permissionGrantedProcessor.onNext(isLocationGranted)
            if (isLocationGranted) {
                startListeningToLocationChanges()
            }
        }
    }

    private fun startListeningToLocationChanges() {
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return
        }
        val locationManager = application.applicationContext.getSystemService(Service.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_INTERVAL,
                LOCATION_REFRESH_DISTANCE,
                object : LocationListener {

                    private var locationsConsumed = 0
                    override fun onLocationChanged(location: Location) {
                        locationsConsumed++
                        locationProcessor.onNext(Coordinates(location.latitude, location.longitude))
                        if (locationsConsumed > CONSUME_LOCATIONS) {
                            locationManager.removeUpdates(this)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String?) {}
                    override fun onProviderDisabled(provider: String?) {}
                })
    }

    private fun checkPermission(permission: String) =
        ActivityCompat.checkSelfPermission(application, permission) == PERMISSION_GRANTED

}
