package com.example.cuciinproject.model

import com.google.gson.annotations.SerializedName

object Laundri {
    data class Laundri(@SerializedName("id_laundri") val id: Int?,
                     @SerializedName("nama_laundri") val namaLaundri : String?,
                       @SerializedName("foto") val foto : String?)
}