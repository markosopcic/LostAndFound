package com.markosopcic.map.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.markosopcic.map.R
import com.markosopcic.reporteditemslib.models.ReportedItem
import kotlinx.android.synthetic.main.info_window.view.*

class MarkerInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    override fun getInfoContents(p0: Marker?): View? {
        val data = p0?.tag as ReportedItem
        val view = LayoutInflater.from(context).inflate(R.layout.info_window, null)
        view.info_window_Title.text = data.type
        view.info_window_Description.text = data.description
        view.info_window_Contact.text = data.contactNumber
        return view
    }

}
