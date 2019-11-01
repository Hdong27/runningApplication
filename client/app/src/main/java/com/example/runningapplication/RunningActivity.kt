package com.example.runningapplication

import android.graphics.Bitmap
import android.view.View
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_club.*
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_running.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class RunningActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
    GoogleMap.SnapshotReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)

        runningMenu.setOnNavigationItemSelectedListener(this)
        runningMenu.selectedItemId = R.id.running

        var stoptime:Long = 0
        start_btn.setOnClickListener {
            chronometer2.base = SystemClock.elapsedRealtime() + stoptime
            chronometer2.start()
            start_btn.visibility = View.GONE
            pause_btn.visibility = View.VISIBLE
        }
        pause_btn.setOnClickListener {
            stoptime = chronometer2.base - SystemClock.elapsedRealtime()
            chronometer2.stop()
            pause_btn.visibility = View.GONE
            start_btn.visibility = View.VISIBLE
        }
        reset_btn.setOnClickListener {
            chronometer2.base = SystemClock.elapsedRealtime()
            stoptime = 0
            chronometer2.stop()
            start_btn.visibility = View.VISIBLE
            pause_btn.visibility = View.GONE

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

            mapFragment.getMapAsync(this)
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

        // 반대로 비트맵 만들기

//        val bImage: ByteArray = Base64.decode(ret, 0)
//        val bais = ByteArrayInputStream(bImage)
//        val bm: Bitmap? = BitmapFactory.decodeStream(bais)
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.snapshot(this)
    }



    private fun takeScreenshotOfView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            Log.d("test1", "test1")
            bgDrawable.draw(canvas)
        } else {
            Log.d("test2", "test2")
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
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
}
