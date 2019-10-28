package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_club.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity()  , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = settings.edit()

        settingMenu.setOnNavigationItemSelectedListener(this)
        settingMenu.selectedItemId = R.id.setting

        btn_logout.setOnClickListener{
            editor.remove("AutoLogin")
            editor.remove("email")
            editor.commit()

            val landingIntent = Intent(this,LandingActivity::class.java)
            startActivity(landingIntent)
        }
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
                val runningIntent = Intent(this, RunningActivity::class.java)
                startActivity(runningIntent)
            }
            R.id.club-> {
                val clubIntent = Intent(this, ClubActivity::class.java)
                startActivity(clubIntent)
            }
            R.id.setting-> {
            }

        }
        return true
    }
}
