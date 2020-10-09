package com.markosopcic.lostandfound.map.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.VisibleRegion

//get the smaller dimension of the screen so we load only items which can actually be shown on screen
fun getSmallerMapWidth(visibleRegion: VisibleRegion): Float {
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

//retrieve bitmap from id
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
