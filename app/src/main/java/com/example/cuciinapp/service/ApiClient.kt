package com.example.cuciinapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        fun getClient(): Retrofit {
            val BASE_URL = "http://172.168.10.7/cuci_in/"
            val retrofit: Retrofit =
                Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit
        }
    }
}