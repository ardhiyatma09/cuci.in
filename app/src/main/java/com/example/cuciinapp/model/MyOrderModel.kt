package com.example.cuciinapp.model

class MyOrderModel {
    private var id_transaksi: Long? = null
    private var id_laundri : Long? = null
    private var nama_laundri : String? = null
    private var id_user : String? = null
    private var status : String? = null
    private var alamat : String? = null
    private var total : String? = null
    private var key : String? = null

    constructor(){}
    constructor(id_transaksi : Long, id_laundri : Long, id_user : String, nama_laundri : String, status: String, alamat: String, total: String){
        this.id_transaksi = id_transaksi
        this.id_laundri = id_laundri
        this.nama_laundri = nama_laundri
        this.id_user = id_user
        this.status = status
        this.alamat = alamat
        this.total = total
    }

    fun getId_transaksi() : Long?{return id_transaksi}
    fun getId_laundri() : Long?{return id_laundri}
    fun getId_user() : String?{return id_user}
    fun getStatus() : String?{return status}
    fun getAlamat() : String?{return alamat}
    fun getTotal() : String?{return total}
    fun getNama_laundri() : String?{return nama_laundri}

    fun getKey() : String{return key!!}
    fun setKey(key : String){this.key = key}

    fun setId_transaksi(id_transaksi : Long){this.id_transaksi = id_transaksi}
    fun setId_laundri(id_laundri : Long){this.id_laundri = id_laundri}
    fun setId_user(id_user: String){this.id_user = id_user}
    fun setStatus(status: String){this.status = status}
    fun setAlamat(alamat : String){this.alamat = alamat}
    fun setTotal(total : String){this.total = total}
    fun setNama_laundri(nama_laundri : String){this.nama_laundri = nama_laundri}

}