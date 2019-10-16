package com.example.runningapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_signup.setOnClickListener {
            Toast.makeText(this.applicationContext, btn_signup.text.toString(), Toast.LENGTH_SHORT).show()
        }

        btn_login.setOnClickListener{
            Toast.makeText(this.applicationContext, btn_login.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
