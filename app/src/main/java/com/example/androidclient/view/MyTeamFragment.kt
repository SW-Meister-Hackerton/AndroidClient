package com.example.androidclient.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidclient.R
import com.example.androidclient.adapter.MyRoomAdapter
import com.example.androidclient.adapter.MyTeamAdapter
import com.example.androidclient.adapter.RoomListAdapter
import com.example.androidclient.data.MyTeamData
import com.example.androidclient.data.request.User
import com.example.androidclient.data.response.RoomResponse
import com.example.androidclient.retrofit.RetrofitClient
import com.example.androidclient.room.DataBase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_myroom.*
import kotlinx.android.synthetic.main.fragment_myteam.*
import kotlinx.android.synthetic.main.fragment_myteam.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyTeamFragment : Fragment() {

    lateinit var mContext : Context
    var teamList : ArrayList<String> = arrayListOf()
    lateinit var adapter : MyTeamAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myteam, container, false)



        view.room_Btn.setOnClickListener {
            setFrag(0)
            teamList.clear()
            adapter.notifyDataSetChanged()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        school_Tv2.text = DataBase.getInstance(mContext)!!.dao().getAll().get(0).school
        name_Tv2.text = DataBase.getInstance(mContext)!!.dao().getAll().get(0).name
        grade_Tv2.text =
            DataBase.getInstance(mContext)!!.dao().getAll().get(0).grade.toString()
        class_Tv2.text =
            DataBase.getInstance(mContext)!!.dao().getAll().get(0).classs.toString()
        getMyTeam()
        setBackGroundColor()
    }

    private fun setFrag(fragNum: Int) {
        val ft = childFragmentManager.beginTransaction() //화면 교체를 위한 트랜잭션
        when (fragNum) {
            0 -> {
                ft.replace(R.id.myTeam_Frame, MyRoomFragment()).commit()
            }
        }
    }

    private fun getMyTeam() {
        RetrofitClient.getInstance()
            .getUserTeam(User(DataBase.getInstance(mContext)!!.dao().getAll().get(0).user))
            .enqueue(object : Callback<List<String>>{
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    if(response.code() == 200){
                        Log.d("Logd", "data : ${response.body()}")
                        teamList.clear()
                        teamList = response.body() as ArrayList<String>
                        adapter = MyTeamAdapter(teamList, mContext)
                        view!!.myTeamRcView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("Logd", t.message.toString())
                }

            })
    }
    private fun setBackGroundColor(){
        when(DataBase.getInstance(requireContext())!!.dao().getAll().get(0).school)
        {
            "대덕" -> {
                constr2.setBackgroundColor(Color.parseColor("#AEF0E6"))
            }
            "대구" -> {
                constr2.setBackgroundColor(Color.parseColor("#AED5F8"))
            }
            "광주" -> {
                constr2.setBackgroundColor(Color.parseColor("#AEB6FF"))
            }
        }
    }
}