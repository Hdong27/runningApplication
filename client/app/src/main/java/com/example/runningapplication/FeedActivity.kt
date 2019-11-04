package com.example.runningapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import com.example.runningapplication.data.model.FriendsRecord
import com.example.runningapplication.service.UserService
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_feed.*

import kotlinx.android.synthetic.main.frienddialog.view.*

import kotlinx.android.synthetic.main.item_friend.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FeedActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        var retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.200.149:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)
        Log.d("asdfasdfsadfgg111", settings.getInt("uid", 0).toString())

        server.findMyFriends(settings.getInt("uid", 0)).enqueue(object : Callback<List<FriendsRecord>> {
            override fun onResponse(call: Call<List<FriendsRecord>>, response: Response<List<FriendsRecord>>) {
                if(response.code()==200){
                    var records: List<FriendsRecord>? = response.body()

                    Toast.makeText(applicationContext, "성공하였습니다.", Toast.LENGTH_SHORT).show()
                    for(record in records!!.iterator()) {
                        Log.d("saasgasfsa", record.userName.toString())
                        Log.d("saasgasfsa", record.userEmail.toString())
                        Log.d("saasgasfsa", record.running!!.image.toString())

                    }

                }else{
                }
            }

            override fun onFailure(call: Call<List<FriendsRecord>>, t: Throwable) {
                Log.d("hi","hi")
                Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        })


        //

        feedMenu.setOnNavigationItemSelectedListener(this)
        feedMenu.selectedItemId = R.id.feed

        search_friend.setOnClickListener {
            var d= Dialog(this)
            var gd=layoutInflater.inflate(R.layout.frienddialog,null)
            val displayRectangle = Rect()
            gd.friendSearch.setOnClickListener {

                var pre = gd.friendList.layoutParams.height
                gd.friendList.removeAllViewsInLayout()
                var friendItem : View

                server.findFriends(email = gd.friendname.text.toString()).enqueue(object : Callback<List<String>> {
                    override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                        if(response.code()==200){
                            for (s in response.body()!!.iterator()){
                                friendItem = layoutInflater.inflate(R.layout.item_friend,null)
                                friendItem.friendEmail.text=s
                                friendItem.addFriend.setOnClickListener {
                                    var param : HashMap<String,Any> = HashMap()
                                    param.put("email",s)
                                    server.friendAdd(param).enqueue(object : Callback<Boolean>{
                                        override fun onResponse(
                                            call: Call<Boolean>,
                                            response: Response<Boolean>
                                        ) {
                                            Toast.makeText(applicationContext,"친구 추가가 완료되었습니다.",Toast.LENGTH_SHORT).show()
                                            d.dismiss()
                                        }

                                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                            Toast.makeText(applicationContext,"크헉",Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }
                                gd.friendList.addView(friendItem)
                            }
                        }else{

                        }
                    }

                    override fun onFailure(call: Call<List<String>>, t: Throwable) {
                        Log.d("hi","hi")
                        Toast.makeText(applicationContext, "김현빈 몰래 밥먹으러감", Toast.LENGTH_SHORT).show()
                    }
                })
                if(gd.friendList.layoutParams.height<pre){
                    gd.layoutParams.height-=(pre-gd.friendList.layoutParams.height)
                }

            }

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            d.setContentView(gd)
            gd.friendCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }
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
