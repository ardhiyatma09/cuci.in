package com.example.cuciinproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cuciinproject.R
import kotlinx.android.synthetic.main.fr_homeuser_activity.*
import com.example.cuciinproject.adapter.LaundriAdapter
import com.example.cuciinproject.model.Laundri.Laundri
import com.example.cuciinproject.model.LaundriResponse.LaundriResponse
import com.example.cuciinproject.service.ApiClient
import com.example.cuciinproject.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FrHomeUser : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fr_homeuser_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvLaundri.layoutManager = GridLayoutManager(activity!!, 2)
        getData()
    }

    fun getData(){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        apiInterface.getAllData().enqueue(object : Callback<LaundriResponse> {
            override fun onFailure(call: Call<LaundriResponse>, t: Throwable) {
                Toast.makeText(activity!!, "Gk iso", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LaundriResponse>, response: Response<LaundriResponse>) {
                if (response.code() == 200) {
                    Log.e("code : ${response.code()}", "${response.body()}")
                    rvLaundri.adapter = LaundriAdapter(response.body()!!.results)
                }else{
                    Log.e("code : ${response.code()}", response.message())
                }
            }
        })
    }
}