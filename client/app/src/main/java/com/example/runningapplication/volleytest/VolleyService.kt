package com.example.runningapplication.volleytest

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object VolleyService {

    val testUrl = "http://70.12.247.54:8080/test"

    fun testVolley(context : Context, success: (Boolean) -> Unit) {
        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val testRequest = object : StringRequest(Method.GET, testUrl,
            Response.Listener { response ->
                println("서버 Response 수신 : $response")
                success(true)
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "서버 Response 가져오기 실패: $error")
                success(false)
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }


        }

        Volley.newRequestQueue(context).add(testRequest)
    }
}