package com.example.runningapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_club.*
import kotlinx.android.synthetic.main.activity_running.*

class RunningActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)

        runningMenu.setOnNavigationItemSelectedListener(this)
        runningMenu.selectedItemId = R.id.running
    }


    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
                val feedIntent = Intent(this, FeedActivity::class.java)
                startActivity(feedIntent)
            }
            R.id.main-> {
                val activityIntent = Intent(this, MainActivity::class.java)
                startActivity(activityIntent)
            }
            R.id.running-> {
            }
            R.id.club-> {
                val clubIntent = Intent(this, ClubActivity::class.java)
                startActivity(clubIntent)
            }
            R.id.setting-> {
                val settingIntent = Intent(this, SettingActivity::class.java)
                startActivity(settingIntent)
            }

        }
        return true
    }
}
