package com.example.cuciinapp.model

import com.google.gson.annotations.SerializedName

object Laundri {
    data class Laundri(
        @SerializedName("id_laundri") val id_laundri: Int?,
        @SerializedName("nama_laundri") val namaLaundri: String?,
        @SerializedName("alamat") val alamat: String?,
        @SerializedName("foto") val foto: String?
    )
}