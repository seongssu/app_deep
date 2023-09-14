package com.android.appdevelop_deep.CreatingGoogleMapsApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.databinding.ActivityCreatingGoogleMapAppBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class CreatingGoogleMapsAppActivity : AppCompatActivity(), OnMapReadyCallback {
    var googleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_google_map_app)

        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!
            .getMapAsync(this)
    }
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
    }
}