package com.android.appdevelop_deep.CreatingGoogleMapsApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.longToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CreatingGoogleMapsAppActivity : AppCompatActivity(), OnMapReadyCallback {
    //OnMapReadyCallback : 인터페이스상속, 추상메서드(onMapReady) override

    private lateinit var mGoogleMap: GoogleMap

    lateinit var fusedLocationClient: FusedLocationProviderClient
    //위치 서비스가 gps를 사용해서 위치를 확인

    lateinit var locationCallback: LocationCallback
    //위치 값 요청에 대한 갱신 정보를 받는 변수

    lateinit var locationPermission: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_google_map_app)

        locationPermission = registerForActivityResult(
            ActivityResultContracts
                .RequestMultiplePermissions()
        ) { results ->
            if (results.all { it.value }) {
                (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!
                    .getMapAsync(this)
            } else {
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
        val seoul = LatLng(37.5212557526595, 127.023032708155)
        mGoogleMap = p0
        mGoogleMap.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            val markerOptions = MarkerOptions()
            markerOptions.apply {
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                // 마커 아이콘 설정, HUE_AZURE : 파란색
                position(seoul)
                title("가로수 길")
                snippet("Tel: 02-308-7894")
                addMarker(markerOptions)
            }
            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this@CreatingGoogleMapsAppActivity)
            updateLocation()
        }
    }

    //현재 위치를 업데이트
    fun updateLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000                                        //업데이트 요청 사이의 간격
            fastestInterval = 500                                // 가능한 빠르게 업데이트를 제공하도록 시도
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY    // 정확한 위치를 업데이트하기위해 시도
        }

        //위치값 요청에 대해서 갱신되는 콜백변수
        locationCallback = object : LocationCallback() {
            //LocationCallback : API를 통해 위치정보를 업데이트 해주는 추상 클래스

            override fun onLocationResult(p0: LocationResult) {
                //위치 업데이트는 주기적으로 발생한다. 그리고 그 업데이트마다 호출되어 결과를 받는다.

                p0?.let {
                    for (location in it.locations) {    //locations : 업데이트된 위치 목록
                        setLastLocation(location)
                    }
                }
            }
        }

        //권한 처리
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            //ActivityCompat.checkSelfPermission : 특정권한이 부여되었는지 확인하는 메서드
            //ACCESS_FINE_LOCATION : "정확한"위치 접근에 대해 권한 허용

            PackageManager.PERMISSION_GRANTED   //PERMISSION_GRANTED : 권한이 부여되었음을 나타내며, 앱은 해당 권한을
            // 사용할 수 있습니다.

            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
                //ACCESS_COARSE_LOCATION : 현재 위치를 정확히 알 필요가 없을떄 사용
            ) != PackageManager.PERMISSION_GRANTED
        //즉, ACCESS_COARSE_LOCATION의 권한이 허용되지않았을때를 말한다.
        ) {
            return
        }

        //gps를 통해서 현재 위치를 확인
        fusedLocationClient.requestLocationUpdates( //requestLocationUpdates : 위치 업데이트를 요청하는 데 사용되는
            // 메서드
            locationRequest,
            locationCallback,   //locationCallback : 위치 업데이트가 발생할 때 호출될 콜백 함수
            Looper.myLooper()!! //Looper.myLooper() : 위치 업데이트가 현재 스레드에서 처리가 된다.
        )
    }
    fun setLastLocation(location : Location){
        val LATLNG = LatLng(location.latitude, location.longitude)
        //LatLng :  Google Maps Android API에서 사용되는 클래스, 지도위의 특정 위치를 나타냄
        // location.latitude : 현재위치의 위도
        // location.longitude : 현재위치의 경도
        val markerOptions = MarkerOptions().position(LATLNG).title("현재 위치")
        val cameraPosition = CameraPosition.Builder().target(LATLNG).zoom(15.0f).build()
        //target(LATLNG) : 카메라의 중심위치
        //zoom(15.0f) : 줌레벨, 중간정도의 확대 수준
        //build() : 카메라 위치 및 줌 레벨을 기반으로 CameraPosition 객체를 최종적으로 빌드하고 반환

        mGoogleMap.addMarker(markerOptions)
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        //CameraUpdateFactory 클래스의 newCameraPosition메서드를 이용해서 업데이트된 위치로 카메라위치를 설정한다.
    }
}