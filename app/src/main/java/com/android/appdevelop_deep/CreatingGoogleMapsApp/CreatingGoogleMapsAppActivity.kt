package com.android.appdevelop_deep.CreatingGoogleMapsApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.longToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CreatingGoogleMapsAppActivity : AppCompatActivity(), OnMapReadyCallback {
    //OnMapReadyCallback : 인터페이스상속, 추상메서드(onMapReady) override

    private lateinit var mGoogleMap: GoogleMap

    lateinit var fusedLocationClient: FusedLocationProviderClient
    //위치 서비스가 gps를 사용해서 위치를 확인

    lateinit var locationCallback:LocationCallback
    //위치 값 요청에 대한 갱신 정보를 받는 변수

    lateinit var locationPermission : ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_google_map_app)

        locationPermission = registerForActivityResult(ActivityResultContracts
            .RequestMultiplePermissions()){results ->
            if(results.all{it.value}){
                (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!
                    .getMapAsync(this)
            }else {
                 longToast("권한 승인이 필요합니다.")
            }
        }

        locationPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    override fun onMapReady(p0: GoogleMap) {

    }
}