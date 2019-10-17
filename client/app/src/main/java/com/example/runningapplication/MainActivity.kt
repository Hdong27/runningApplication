package com.example.runningapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.runningapplication.volleytest.VolleyService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_signup.setOnClickListener {
            Toast.makeText(this.applicationContext, btn_signup.text.toString(), Toast.LENGTH_SHORT).show()
        }

        btn_login.setOnClickListener{
            VolleyService.testVolley(this) { testSuccess ->
                if(testSuccess) {
                    Toast.makeText(this, "통신 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "통신 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
