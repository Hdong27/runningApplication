package com.example.runningapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_running.*
import android.Manifest
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.SphericalUtil.computeDistanceBetween
import org.jetbrains.anko.*
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream

class RunningActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback, GoogleMap.SnapshotReadyCallback {

    private var polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
    private lateinit var mMap : GoogleMap
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack
    private var flag : Int = 1
    private var dir : Double = 0.0
    private var stoptime:Long = 0
    private lateinit var marker : Marker

    // 위치 정보를 얻기 위한 초기화
    private fun locationinit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    // 위치 정보를 찾고 나서 인스턴스화하는 클래스
    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {
                val latLng = LatLng(latitude, longitude)

                mMap.clear()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                mMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))

                if(flag == 1) { // 시작 전, 정지 버튼 클릭시
                    Toast.makeText(applicationContext, latLng.toString(), Toast.LENGTH_SHORT).show()
                } else if(flag == 2) {  // 시작 버튼 클릭시
                    mMap.addPolyline(polyLineOptions)
                    if(polyLineOptions.points.size > 1) {
                        dir += computeDistanceBetween(polyLineOptions.points[polyLineOptions.points.size - 2], polyLineOptions.points[polyLineOptions.points.size - 1])
                    }
                    totalDir.setText(String.format("%.2f", dir))
                    var time : CharSequence = chronometer2.text
                    var totalTime : Int = ((time[0] - '0') * 10 + (time[1] - '0')) * 60 + ((time[3] - '0') * 10 + (time[4] - '0'))
                    totalCal.setText(String.format("%.1fkcal", totalTime * 0.55))
                    totalVec.setText(String.format("%.2fm/s", dir / totalTime))
                    // Toast.makeText(applicationContext, polyLineOptions.points[polyLineOptions.points.size-1].toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext, totalTime.toString(), Toast.LENGTH_SHORT).show()
                } else {  // 일시정지 버튼 클릭시
                    polyLineOptions.add(latLng)
                    mMap.addPolyline(polyLineOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    mMap.addMarker(MarkerOptions().position(latLng).visible(false))
                    if(polyLineOptions.points.size > 0) {
                        Toast.makeText(applicationContext, polyLineOptions.points[polyLineOptions.points.size-1].toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    override fun onSnapshotReady(p0: Bitmap) {
        var tb = Bitmap.createBitmap(p0, 0, 0, p0.width, p0.height)
        val baos = ByteArrayOutputStream()

        tb.compress(
            Bitmap.CompressFormat.PNG,
            100,
            baos
        ) //bm is the bitmap object

        val b: ByteArray = baos.toByteArray()

        var ret = Base64.encodeToString(b, Base64.DEFAULT)
        Log.d("test", ret)
        // ret를 img로 보내서 DB 저장

        // 반대로 비트맵 만들기
//        val bImage: ByteArray = Base64.decode(ret, 0)
//        val bais = ByteArrayInputStream(bImage)
//        val bm: Bitmap? = BitmapFactory.decodeStream(bais)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)

        runningMenu.setOnNavigationItemSelectedListener(this)
        runningMenu.selectedItemId = R.id.running

        // 버튼 스톱워치
        start_btn.setOnClickListener {
            polyLineOptions = PolylineOptions().width(5f).color(Color.RED)
            chronometer2.base = SystemClock.elapsedRealtime() + stoptime
            chronometer2.start()

            flag = 2
            Toast.makeText(this.applicationContext, "시작", Toast.LENGTH_SHORT).show()

            start_btn.visibility = View.GONE
            pause_btn.visibility = View.VISIBLE
        }
        pause_btn.setOnClickListener {
            polyLineOptions = PolylineOptions().width(0f)
            stoptime = chronometer2.base - SystemClock.elapsedRealtime()
            chronometer2.stop()

            flag = 3
            Toast.makeText(this.applicationContext, "일시정지", Toast.LENGTH_SHORT).show()

            pause_btn.visibility = View.GONE
            start_btn.visibility = View.VISIBLE
        }

        // 저장을 할건지 말건지
        reset_btn.setOnClickListener {
            chronometer2.base = SystemClock.elapsedRealtime()
            stoptime = 0
            totalDir.setText("0.00")
            totalCal.setText("0kcal")
            totalVec.setText("0m/s")
            chronometer2.stop()

            flag = 1
            mMap.clear()
            polyLineOptions = PolylineOptions().width(5f).color(Color.RED)

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
            Toast.makeText(this.applicationContext, "종료", Toast.LENGTH_SHORT).show()

            start_btn.visibility = View.VISIBLE
            pause_btn.visibility = View.GONE

            mMap.snapshot(this)
        }
        // 버튼 스톱워치

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        locationinit()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "GPS가 켜져 있는지 확인해 주십시오.", Toast.LENGTH_SHORT).show()
        permissionCheck(cancel = {showPermissionInfoDialog()}, ok = {addLocationListener()})
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
                val feedIntent = Intent(this, FeedActivity::class.java)
                feedIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(feedIntent)
            }
            R.id.main-> {
                val activityIntent = Intent(this, MainActivity::class.java)
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(activityIntent)
            }
            R.id.running-> {
            }
            R.id.club-> {
                val clubIntent = Intent(this, ClubActivity::class.java)
                clubIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(clubIntent)
            }
            R.id.setting-> {
                val settingIntent = Intent(this, SettingActivity::class.java)
                settingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(settingIntent)
            }

        }
        return true
    }

    // 위치 요청 메소드
    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val SEOUL = LatLng(37.56, 126.97)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL).title("서울").snippet("한국의 수도").visible(false)

        mMap.addMarker(markerOptions.visible(false))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
        //googleMap.snapshot(this)
    }

    private val gps_request_code = 1000
    private fun permissionCheck(cancel:() -> Unit, ok:() -> Unit) {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                cancel()
            }
            else
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), gps_request_code)
        }
        else
            ok()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            gps_request_code -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    addLocationListener()
                else {
                    toast("권한 거부 됨")
                    finish()
                }
            }
        }
    }

    private fun showPermissionInfoDialog() {
        alert("지도 정보를 얻으려면 위치 권한이 필요합니다", "") {
            yesButton {
                ActivityCompat.requestPermissions(this@RunningActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), gps_request_code)
            }
            noButton {
                toast("권한 거부 됨")
                finish()
            }
        }.show()
    }
}
