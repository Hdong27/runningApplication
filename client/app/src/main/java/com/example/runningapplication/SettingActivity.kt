package com.example.runningapplication

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_setting.*
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import com.example.runningapplication.service.UserService
import kotlinx.android.synthetic.main.genderdialog.*
import kotlinx.android.synthetic.main.genderdialog.view.*
import kotlinx.android.synthetic.main.heightdialog.*
import kotlinx.android.synthetic.main.weightdialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.lang.Exception

class SettingActivity : AppCompatActivity()  , BottomNavigationView.OnNavigationItemSelectedListener{

    val REQUEST_CODE = 0;

    var retrofit = Retrofit.Builder()
        .baseUrl("http://52.79.200.149:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var server = retrofit.create(UserService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = settings.edit()
        settingMenu.setOnNavigationItemSelectedListener(this)
        settingMenu.selectedItemId = R.id.setting

        val displayRectangle = Rect()
        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        var size=(displayRectangle.width()*0.3f).toInt()
        ProfileImage.layoutParams.height=size
        ProfileImage.layoutParams.width=size

        genderVal.text=settings.getString("gender","여성")
        email.text=settings.getString("email","dudaduada")
        name.text=settings.getString("name","옹붐바바")
        heightVal.text=settings.getString("height","181 cm")
        weightVal.text=settings.getString("weight","70 kg")

        if(!settings.getString("img","def").equals("null")){
            ProfileImage.setImageBitmap(
                BitmapFactory.decodeStream(
                    ByteArrayInputStream(
                        Base64.decode(settings.getString("img","def"), 0))))
        }
        ProfileImage.background = ShapeDrawable(OvalShape())
        ProfileImage.clipToOutline = true
        ProfileImage.requestLayout()

        ProfileImage.setOnClickListener {
            var imageIntent = Intent()
            imageIntent.setType("image/*")
            imageIntent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(imageIntent, REQUEST_CODE)
        }

        ProfileGender.setOnClickListener {
            var d=Dialog(this)
            var gd=layoutInflater.inflate(R.layout.genderdialog,null)
            val displayRectangle = Rect()

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            gd.minimumWidth = (displayRectangle.width()*0.8f).toInt()
            d.setContentView(gd)
            var gg=d.genderGroup
            if (genderVal.text.toString().equals("남성"))gg.genderMale.isChecked=true
            else gg.genderFemale.isChecked=true

            d.genderConfirm.setOnClickListener {
                genderVal.text=d.findViewById<RadioButton>(gg.checkedRadioButtonId).text.toString()
                d.dismiss()
            }
            d.genderCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }

        ProfileHeight.setOnClickListener {
            var d=Dialog(this)
            var hd=layoutInflater.inflate(R.layout.heightdialog,null)
            val displayRectangle = Rect()

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            hd.minimumWidth = (displayRectangle.width()*0.8f).toInt()
            d.setContentView(hd)
            var hp:NumberPicker=d.heightPicker
            hp.maxValue=270
            hp.minValue=100
            Log.d("hereHW",heightVal.text.toString())
            hp.value=heightVal.text.toString().substring(0,(heightVal.text.toString().length)-3).toInt()
            var heights= Array<String>(171,{""})
            for (i in 100..270){
                heights[i-100]=i.toString()+" cm"
            }
            hp.displayedValues=heights
            d.heightConfirm.setOnClickListener {

                var tmpHeight=d.heightPicker.value.toString()+" cm"
                var parameters = HashMap<String,Any>()
                parameters.put("height",tmpHeight)
                parameters.put("uid", settings.getInt("uid", 0))

                server.setProfileHeight(parameters).enqueue(object :Callback<Boolean>{
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if(response.body()==true){

                            heightVal.text=tmpHeight
                            editor.putString("height",tmpHeight)
                            Toast.makeText(applicationContext, settings.getString("height","00000"),Toast.LENGTH_LONG).show()
                            editor.commit()
                        }
                        else{
                            Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(applicationContext,"통신 에러", Toast.LENGTH_SHORT).show()
                    }
                })
                d.dismiss()
            }
            d.heightCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }

        ProfileWeight.setOnClickListener {
            var d=Dialog(this)
            var wd=layoutInflater.inflate(R.layout.weightdialog,null)
            val displayRectangle = Rect()

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            wd.minimumWidth = (displayRectangle.width()*0.8f).toInt()
            d.setContentView(wd)
            var wp:NumberPicker=d.weightPicker
            wp.maxValue=200
            wp.minValue=20
            Log.d("hereW",weightVal.text.toString())
            wp.value=weightVal.text.toString().substring(0,(weightVal.text.toString().length)-3).toInt()
            var weights= Array<String>(181,{""})
            for (i in 20..200){
                weights[i-20]=i.toString()+" kg"
            }
            wp.displayedValues=weights
            d.weightConfirm.setOnClickListener {
                var tmpWeight=d.weightPicker.value.toString()+" kg"
                var parameters = HashMap<String,Any>()
                parameters.put("weight",tmpWeight)
                parameters.put("uid", settings.getInt("uid", 0))
                server.setProfileWeight(parameters).enqueue(object :Callback<Boolean>{
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if(response.body()==true){
                            weightVal.text=tmpWeight
                            editor.putString("height",tmpWeight)
                            editor.commit()

                        }
                        else{
                            Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(applicationContext,"통신 에러", Toast.LENGTH_SHORT).show()
                    }
                })
                d.dismiss()
            }
            d.weightCancel.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }

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
                feedIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(feedIntent)
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
            }

        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK){
                var imageUri = data!!.data
                var imgStream = contentResolver.openInputStream(imageUri!!)
                var img =BitmapFactory.decodeStream(imgStream)

                while(img.width>500 && img.height>500){
                    img.width/=2
                    img.height/=2
                }

                var baos = ByteArrayOutputStream()
                img.compress(Bitmap.CompressFormat.PNG,100,baos)
                var encodedImg = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

                var settings: SharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = settings.edit()
                var parameters = HashMap<String,Any>()
                parameters.put("image", encodedImg)
                parameters.put("uid", settings.getInt("uid", 0))
                server.setProfileImage(parameters).enqueue(object :Callback<Boolean>{
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if(response.body()==true){
                            ProfileImage.setImageBitmap(img)
                            editor.putString("img",encodedImg)
                            editor.commit()
                        }
                        else{
                            Toast.makeText(applicationContext,"이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(applicationContext,"통신 에러", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else if(resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
}
