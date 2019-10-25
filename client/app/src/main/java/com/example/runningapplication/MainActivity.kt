package com.example.runningapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    MainFragment.OnFragmentInteractionListener,
    FeedFragment.OnFragmentInteractionListener,
    ClubFragment.OnFragmentInteractionListener,
    MailFragment.OnFragmentInteractionListener,
    ActivityFragment.OnFragmentInteractionListener,
    BottomNavigationView.OnNavigationItemSelectedListener{

    val fm=supportFragmentManager

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fm.beginTransaction().add(R.id.fragment,MainFragment()).commit()

        val myMenu=menuBar
        myMenu.setOnNavigationItemSelectedListener (this)
    }

    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.navigation_home -> {

                fm.beginTransaction().replace(R.id.fragment,MainFragment()).commit()
            }
            R.id.navigation_activity -> {
                fm.beginTransaction().replace(R.id.fragment,ActivityFragment()).commit()
            }
            R.id.navigation_feed -> {
                fm.beginTransaction().replace(R.id.fragment,FeedFragment()).commit()
            }
            R.id.navigation_club -> {
                fm.beginTransaction().replace(R.id.fragment,ClubFragment()).commit()
            }
            R.id.navigation_mail -> {
                fm.beginTransaction().replace(R.id.fragment,MailFragment()).commit()
            }
        }
        return true
    }

}
