package com.example.runningapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_club.*

class ClubActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)
        clubMenu.setOnNavigationItemSelectedListener(this)
        clubMenu.selectedItemId = R.id.club
    }
    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
                Log.d("즐","김현빈바보")
                val feedIntent = Intent(this, FeedActivity::class.java)
                startActivity(feedIntent)
            }
            R.id.main-> {
                Log.d("즐","main")
                val activityIntent = Intent(this, MainActivity::class.java)
                startActivity(activityIntent)
            }
            R.id.running-> {
                Log.d("즐","running")
                val runningIntent = Intent(this, RunningActivity::class.java)
                startActivity(runningIntent)
            }
            R.id.club-> {
            }
            R.id.setting-> {
                val settingIntent = Intent(this, SettingActivity::class.java)
                startActivity(settingIntent)
            }

        }
        return true
    }
}
