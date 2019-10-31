package com.example.runningapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.200.149:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(UserService::class.java)

        emailChk.setOnClickListener{
            var parameters = HashMap<String,Any>()
            parameters.put("email",this.email.text.toString())
            server.emailCheck(parameters).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

                    if(response.body()==true){
                        Toast.makeText(applicationContext, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "중복되는 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(applicationContext, "중복되는 이메일입니다.", Toast.LENGTH_SHORT).show()
                }

            })
        }

        btn_submit.setOnClickListener {
            Log.d("Here?","Here?")
            var parameters = HashMap<String,Any>()
            parameters.put("name", this.username.text.toString())
            parameters.put("height",this.height.text.toString())
            parameters.put("weight", this.weight.text.toString())
            parameters.put("email",this.email.text.toString())
            parameters.put("password",this.pwd.text.toString())
            parameters.put("gender",findViewById<RadioButton>(this.gender.checkedRadioButtonId).text.toString())
            Log.d("",parameters.toString())
            server.signUp(parameters).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    Log.d("ㅠㅠ",response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("hi","hi")
                    Toast.makeText(applicationContext, "가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }

            })
        }

        back.setOnClickListener {
            val landingIntent = Intent(this, LandingActivity::class.java)
            startActivity(landingIntent)
        }

    }
}
