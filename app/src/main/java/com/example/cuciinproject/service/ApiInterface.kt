package com.example.cuciinproject.service

//import com.example.testretrofitannas.model.Movie.Movie
//import com.example.testretrofitannas.model.MovieResponse.MovieResponse
import com.example.cuciinproject.model.LaundriResponse.LaundriResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("tampil_laundri.php")
    fun getAllData() : Call<LaundriResponse>
}