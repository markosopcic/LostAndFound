package com.markosopcic.lostandfound.map.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import base.ui.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.markosopcic.lostandfound.R
import com.markosopcic.lostandfound.R.layout
import com.markosopcic.lostandfound.locationlib.model.Coordinates
import com.markosopcic.lostandfound.map.ui.MapViewState.*
import com.markosopcic.lostandfound.reporteditemslib.models.ReportedItem
import kotlinx.android.synthetic.main.fragment_maps.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.random.Random

private const val LANDMASS_ZOOM_LEVEL = 5
private const val CITY_ZOOM_LEVEL = 15F

class MapsFragment : BaseFragment<MapViewState>(layout.fragment_maps) {
    override val viewModel: MapViewModel by viewModel()

    private lateinit var map: GoogleMap

    private val infoWindowAdapter by lazy { MarkerInfoWindowAdapter(requireContext()) }

    private val moneyMarker by lazy {
        BitmapDescriptorFactory.fromBitmap(
            R.drawable.ic_baseline_attach_money_24.toBitmap(
                requireContext(),
                Color.GREEN
            )
        )
    }
    private val keyMarker
        by lazy {
            BitmapDescriptorFactory.fromBitmap(
                R.drawable.ic_baseline_vpn_key_24.toBitmap(
                    requireContext(),
                    Color.YELLOW
                )
            )
        }

    override fun render(viewState: MapViewState) = when (viewState) {
        is LocationViewState -> updateLocation(viewState)
        is LocationPermissionStatus -> updateLocationPermissionUI(viewState.hasPermission)
        is NearReportedItems -> updatePins(viewState.items)
    }

    private fun updatePins(items: List<ReportedItem>) {
        if (!::map.isInitialized) {
            return
        }
        Timber.d("PINS ${items.size}")

        items.forEach {
            val position = LatLng(it.latitude, it.longitude)

            val marker = map.addMarker(
                MarkerOptions()
                    .position(position)
                    .icon(if (Random.nextBoolean()) moneyMarker else keyMarker)

            )
            marker.tag = it
        }

    }

    override fun initialiseView() {
        map_map.getMapAsync { googleMap ->
            this.map = googleMap
            viewModel.mapInitialized()
            map.setOnCameraMoveListener(::cameraMoved)
            map.setInfoWindowAdapter(infoWindowAdapter)
        }

        map_options.setOnClickListener {
            viewModel.showOptions()
        }
    }

    private fun updateLocation(location: LocationViewState) {
        if (!::map.isInitialized) {
            return
        }
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ), CITY_ZOOM_LEVEL
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationPermissionUI(permission: Boolean) {
        if (!::map.isInitialized) {
            return
        }

        map.isMyLocationEnabled = permission
        map.uiSettings.isMyLocationButtonEnabled = permission
    }

    private fun cameraMoved() {
        if (!::map.isInitialized || map.cameraPosition.zoom < LANDMASS_ZOOM_LEVEL) {
            return
        }

        with(map.projection.visibleRegion) {
            viewModel.mapRecentered(
                Coordinates(
                    latLngBounds.center.latitude,
                    latLngBounds.center.longitude
                ), getSmallerMapWidth(map.projection.visibleRegion).toDouble()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map_map.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        map_map.onStart()
    }

    override fun onResume() {
        super.onResume()
        map_map.onResume()
    }

    override fun onStop() {
        map_map.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        map_map.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map_map.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_map.onLowMemory()
    }
}
