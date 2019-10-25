package com.example.runningapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = settings.edit()

        var retrofit = Retrofit.Builder()
            .baseUrl(R.string.ip as String)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)

        btn_submit.setOnClickListener {
            var parameters = HashMap<String,Any>()
            parameters.put("email",this.email.text.toString())
            parameters.put("password",this.pwd.text.toString())

            server.login(parameters).enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.code()==200){
                        loginChk.text="로그인 되었습니다."
                        var user:User? = response.body()
                        Log.d("user",user?.email.toString())
                        Log.d("user",user?.password.toString())
                        editor.putBoolean("AutoLogin",true)
                        editor.putString("email",user?.email.toString())
                        editor.commit()
                    }else{
                        loginChk.text="로그인 실패. 다시 ㄱ"
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("hi","hi")
                    loginChk.text=t.toString()
                    Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        back.setOnClickListener {
            val landingIntent = Intent(this, LandingActivity::class.java)
            startActivity(landingIntent)
        }

    }
}
