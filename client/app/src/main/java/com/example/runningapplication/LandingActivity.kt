package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)


        Log.d("here?",settings.getString("email","no"))
        Log.d("here??",settings.getBoolean("AutoLogin",false).toString())
        if(settings.getBoolean("AutoLogin",false)){
            Log.d("here???","ㅗ")
            val rIntent = Intent(this, MainActivity::class.java)
            startActivity(rIntent)
        }else{
            setContentView(R.layout.activity_landing)
        }

        btn_signup.setOnClickListener {
            Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show()
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }

        btn_login.setOnClickListener{
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

}
