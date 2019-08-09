package com.example.cuciinapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cuciinapp.R
import com.example.cuciinapp.adapter.LaundriAdapter
import com.example.cuciinapp.item.BannerCarouselItem
import com.example.cuciinapp.model.Banner
import com.example.cuciinapp.model.LaundriResponse.LaundriResponse
import com.example.cuciinapp.service.ApiClient
import com.example.cuciinapp.service.ApiInterface
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fr_homeuser_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FrHomeUser : Fragment() {

    private var groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fr_homeuser_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvLaundri.layoutManager = GridLayoutManager(activity!!, 2) as RecyclerView.LayoutManager?
        getData()

        val urlGambarBerita = "http://172.168.10.7/cuci_in/images/"
        val promos = listOf(
            Banner(
                image = urlGambarBerita + "/laundri_1.jpg"
            ),
            Banner(

                image = urlGambarBerita + "/laundri_2.jpg"
            ),
            Banner(
                image = urlGambarBerita + "/laundri_3.jpg"
            ),
            Banner(

                image = urlGambarBerita + "/laundri_4.jpg"
            ),
            Banner(
                image = urlGambarBerita + "/laundri_5.jpg"
            ),
            Banner(

                image = urlGambarBerita + "/laundri_6.jpg"
            )
        )

        rvMain.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = groupAdapter
        }

        // declare banner carousel
        val bannerCarouselItem = BannerCarouselItem(promos, childFragmentManager)


        groupAdapter.add(bannerCarouselItem)
    }


    fun getData() {
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        apiInterface.getAllData().enqueue(object : Callback<LaundriResponse> {
            override fun onFailure(call: Call<LaundriResponse>, t: Throwable) {
                Toast.makeText(activity!!, "Gk iso", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LaundriResponse>, response: Response<LaundriResponse>) {
                if (response.code() == 200) {
                    Log.e("code : ${response.code()}", "${response.body()}")
                    rvLaundri.adapter = LaundriAdapter(response.body()!!.results)
                } else {
                    Log.e("code : ${response.code()}", response.message())
                }
            }
        })
    }
}