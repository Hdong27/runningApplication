package com.example.runningapplication

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.runningapplication.data.model.User
import com.example.runningapplication.service.RunningService
import kotlinx.android.synthetic.main.fragment_main_record.view.*
import kotlinx.android.synthetic.main.item_record.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.util.jar.Attributes

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainRecordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainRecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 서버 통신

        var settings: SharedPreferences = activity!!.getSharedPreferences("loginStatus", Context.MODE_PRIVATE)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://70.12.247.54:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(RunningService::class.java)

        var recordlist=inflater.inflate(R.layout.fragment_main_record, container, false)

        server.findRunning(settings.getInt("uid", 0)).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code()==200){
                    var user: User? = response.body()
                    Log.d("제발", user?.runningData.toString())
                    Toast.makeText(activity, "성공하였습니다.", Toast.LENGTH_SHORT).show()
                    for(running in user!!.runningData!!.iterator()) {
                        var recorditem=inflater.inflate(R.layout.item_record,null)
                        recorditem.today.text=running.rid.toString()
                        recorditem.day.text=running.endtime.toString()
                        recorditem.distance.text= running.distance.toString()
                        var ttmp = running.image
                        val bImage: ByteArray = Base64.decode(ttmp, 0)
                        val bais = ByteArrayInputStream(bImage)
                        val bm: Bitmap? = BitmapFactory.decodeStream(bais)
                        recorditem.mapImage.setImageBitmap(bm)
                        recorditem.setOnClickListener {

                        }

                        recordlist.recordList.addView(recorditem)
                        Log.d("test12314", running.toString())
                    }

                }else{
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("hi","hi")
                Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        })

        // Inflate the layout for this fragment



        return recordlist
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainRecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
