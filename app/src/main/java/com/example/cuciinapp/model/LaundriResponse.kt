package com.example.cuciinapp.model

import com.google.gson.annotations.SerializedName

object LaundriResponse {
    data class LaundriResponse(
        @SerializedName("status") var status: String,
        @SerializedName("berita") val results: ArrayList<Laundri.Laundri>
    )
}