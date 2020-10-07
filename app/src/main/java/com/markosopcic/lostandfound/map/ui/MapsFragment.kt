package com.markosopcic.lostandfound.map.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import base.ui.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.VisibleRegion
import com.markosopcic.lostandfound.R
import com.markosopcic.lostandfound.R.layout
import com.markosopcic.lostandfound.locationlib.model.Coordinates
import com.markosopcic.lostandfound.map.ui.MapViewState.*
import com.markosopcic.lostandfound.reporteditemslib.models.ReportedItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.random.Random

private const val LADNMASS_ZOOM_LEVEL = 5

class MapsFragment : BaseFragment<MapViewState>(layout.fragment_maps) {
    override val viewModel: MapViewModel by viewModel()

    private lateinit var map: GoogleMap

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

            map.addMarker(
                    MarkerOptions()
                            .position(position)
                            .title("Yes")
                            .icon(if (Random.nextBoolean()) moneyMarker else keyMarker)
                            .draggable(true)
            )
        }

    }

    override fun initialiseView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->
            this.map = googleMap
            viewModel.mapInitialized()
            map.setOnCameraMoveListener(::cameraMoved)
        }
    }

    private fun updateLocation(location: LocationViewState) {
        if (!::map.isInitialized) {
            return
        }
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
        if (!::map.isInitialized || map.cameraPosition.zoom < LADNMASS_ZOOM_LEVEL) {
            return
        }

        with(map.projection.visibleRegion) {
            viewModel.mapRecentered(
                    Coordinates(
                            latLngBounds.center.latitude,
                            latLngBounds.center.longitude
                    ), getSmallerMapWidth().toDouble()
            )
        }
    }

    fun Int.toBitmap(context: Context, tintColor: Int? = null): Bitmap? {

        // retrieve the actual drawable
        val drawable = ContextCompat.getDrawable(context, this) ?: return null
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
        )

        // add the tint if it exists
        tintColor?.let {
            DrawableCompat.setTint(drawable, tintColor)
        }
        // draw it onto the bitmap
        val canvas = Canvas(bm)
        drawable.draw(canvas)
        return bm
    }

    //get the smaller dimension of the screen so we load only items which can actually be shown on screen
    private fun getSmallerMapWidth(): Float {
        val visibleRegion: VisibleRegion = map.projection.visibleRegion

        val farRight = visibleRegion.farRight
        val farLeft = visibleRegion.farLeft
        val nearRight = visibleRegion.nearRight
        val nearLeft = visibleRegion.nearLeft

        val distanceWidth = FloatArray(2)
        Location.distanceBetween(
                (farRight.latitude + nearRight.latitude) / 2,
                (farRight.longitude + nearRight.longitude) / 2,
                (farLeft.latitude + nearLeft.latitude) / 2,
                (farLeft.longitude + nearLeft.longitude) / 2,
                distanceWidth
        )

        val distanceHeight = FloatArray(2)
        Location.distanceBetween(
                (farRight.latitude + nearRight.latitude) / 2,
                (farRight.longitude + nearRight.longitude) / 2,
                (farLeft.latitude + nearLeft.latitude) / 2,
                (farLeft.longitude + nearLeft.longitude) / 2,
                distanceHeight
        )
        return if (distanceWidth[0] > distanceHeight[0]) {
            distanceHeight[0] / 2
        } else {
            distanceWidth[0] / 2
        }
    }

}
