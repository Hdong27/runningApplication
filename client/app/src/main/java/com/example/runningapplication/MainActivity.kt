package com.example.runningapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_club.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    MainRecordFragment.OnFragmentInteractionListener,
    MainAchieveFragment.OnFragmentInteractionListener,
    MainLevelFragment.OnFragmentInteractionListener{

    private var tabLayout: TabLayout?=null
    var viewPager: ViewPager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainMenu.setOnNavigationItemSelectedListener(this)
        mainMenu.selectedItemId = R.id.main

        viewPager=mainViewPager as ViewPager
        setupViewPager(viewPager!!)
        tabLayout = mainTabs as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.getTabAt(0)!!.setText("기록")
        tabLayout!!.getTabAt(1)!!.setText("달성 기록")
        tabLayout!!.getTabAt(2)!!.setText("러닝 레벨")
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MainRecordFragment(), "record")
        adapter.addFragment(MainAchieveFragment(), "achieve")
        adapter.addFragment(MainLevelFragment(), "level")
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment,title: String){
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onNavigationItemSelected(p0: MenuItem) : Boolean {
        when(p0.itemId){
            R.id.feed -> {
                val feedIntent = Intent(this, FeedActivity::class.java)
                startActivity(feedIntent)
            }
            R.id.main-> {
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
                val settingIntent = Intent(this, SettingActivity::class.java)
                startActivity(settingIntent)
            }

        }
        return true
    }
}
