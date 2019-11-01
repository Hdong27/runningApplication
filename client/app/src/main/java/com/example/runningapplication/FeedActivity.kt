package com.example.runningapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        feedMenu.setOnNavigationItemSelectedListener(this)
        feedMenu.selectedItemId = R.id.feed
    }
    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
            }
            R.id.main-> {
                val activityIntent = Intent(this, MainActivity::class.java)
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(activityIntent)
            }
            R.id.running-> {
                val runningIntent = Intent(this, RunningActivity::class.java)
                runningIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(runningIntent)
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
