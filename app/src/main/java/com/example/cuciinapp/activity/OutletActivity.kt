package com.example.cuciinapp.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.cuciinapp.R
import com.example.cuciinapp.adapter.LaundriAdapter
import com.example.cuciinapp.model.Laundri
import com.example.cuciinapp.model.LaundriResponse
import com.example.cuciinapp.service.ApiClient
import com.example.cuciinapp.service.ApiInterface
import kotlinx.android.synthetic.main.fr_homeuser_activity.*
import kotlinx.android.synthetic.main.outlet_activity.*
import kotlinx.android.synthetic.main.register_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutletActivity : AppCompatActivity() {

    var id_laundri : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outlet_activity)

        id_laundri = intent.getIntExtra("id_laundri", 0)
//        Toast.makeText(this, "${id_laundri}", Toast.LENGTH_SHORT).show()
        getLaundri(id_laundri!!)

        btn_back.setOnClickListener {
            finish()
        }
    }

    fun getLaundri(id_laundri : Int){
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        apiInterface.getLaundri(id_laundri).enqueue(object : Callback<LaundriResponse.LaundriResponse> {
            override fun onFailure(call: Call<LaundriResponse.LaundriResponse>, t: Throwable) {
                Toast.makeText(this@OutletActivity, "Gk iso", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LaundriResponse.LaundriResponse>, response: Response<LaundriResponse.LaundriResponse>) {
                if (response.code() == 200) {
                    Log.e("code : ${response.code()}", "${response.body()}")
                    val laundri = response.body()!!.results.get(0)
                    tvNama.text = laundri.namaLaundri
                    tvAlamat.text = laundri.alamat

                }else{
                    Log.e("code : ${response.code()}", response.message())
                }
            }
        })
    }
}