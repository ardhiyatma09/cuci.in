package com.example.cuciinapp.service

//import com.example.testretrofitannas.model.Movie.Movie
//import com.example.testretrofitannas.model.MovieResponse.MovieResponse
import com.example.cuciinapp.model.LaundriResponse.LaundriResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("tampil_laundri.php")
    fun getAllData(): Call<LaundriResponse>

    @GET("tampil_id_laundri.php")
    fun getLaundri(@Query("id_laundri") id_laundri: Int): Call<LaundriResponse>
}