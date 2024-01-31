package com.example.wayvistaanandroidapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.wayvistaanandroidapplication.R
import com.example.wayvistaanandroidapplication.databinding.ActivityMainBinding
import com.example.wayvistaanandroidapplication.databinding.ActivityMapViewBinding
import com.example.wayvistaanandroidapplication.utilities.MapLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapViewBinding: ActivityMapViewBinding
    private lateinit var mapLocation: List<MapLocation>
    private var currentData: Int = 0
    private var latitide: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapViewBinding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(mapViewBinding.root)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapsFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapLocation = MapHistory.locationData
        currentData = MapHistory.currentData

        updateButtonVisibility()

        mapViewBinding.fab.setOnClickListener{
            finish()
        }

        mapViewBinding.backBtn.setOnClickListener {
            currentData -= 1
            updateButtonVisibility()
            loadLocation()
        }

        mapViewBinding.fwrdBtn.setOnClickListener {
            currentData += 1
            updateButtonVisibility()
            loadLocation()
        }
    }

    private fun updateButtonVisibility() {
        mapViewBinding.backBtn.visibility = if (currentData == 0) View.GONE else View.VISIBLE
    }

    private fun loadLocation() {
        if (currentData == mapLocation.size - 1) {
            mapViewBinding.fwrdBtn.visibility = View.GONE
        } else {
            mapViewBinding.fwrdBtn.visibility = View.VISIBLE
        }

        val latLng = LatLng(mapLocation[currentData].latitude, mapLocation[currentData].longitude)
        googleMap.addMarker(
            MarkerOptions().position(latLng).title(
                mapLocation[currentData].districts + mapLocation[currentData].states
            )
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
        mapViewBinding.locationHis.text = mapLocation[currentData].time
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        loadLocation()
    }

}




















