package com.example.cuciinapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.cuciinapp.R
import com.example.cuciinapp.model.LaundriResponse
import com.example.cuciinapp.service.ApiClient
import com.example.cuciinapp.service.ApiInterface
import kotlinx.android.synthetic.main.outlet_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutletActivity : AppCompatActivity() {

    var id_laundri : Int? = null
    var namaLaundri : String? = null
    var idjumlahpakaian: String? = null
    var id_jumlahtas: String? = null
    var id_jumlahsepatu: String? = null
    var subpakaian: String? = null
    var subtas: String? = null
    var subsepatu: String? = null
    var grandtotal: String? = null
    var ongkir: String? = null
    var total: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outlet_activity)

        id_laundri = intent.getIntExtra("id_laundri", 0)
        namaLaundri = intent.getStringExtra("namaLaundri")
//        Toast.makeText(this, "${id_laundri}", Toast.LENGTH_SHORT).show()
        getLaundri(id_laundri!!)

        id_toolbar.visibility = View.INVISIBLE

        btn_min.setOnClickListener {

            val a = idjumlah.text.toString().toInt()
            val b = this.kurang(a)

            if (b.toString() == "-1") {
                Toast.makeText(this@OutletActivity, "jumlah tidak boleh kurang dari 0", Toast.LENGTH_SHORT).show()
            } else {
                idjumlah.setText(b.toString())
                idjumlahpakaian = b.toString()

                val c = this.Total(idjumlahpakaian!!.toInt(), id_jumlahtas!!.toInt(), id_jumlahsepatu!!.toInt())
                id_jumlah_total.text = "Rp " + c.toString()
            }
        }

        btn_plus.setOnClickListener {

            val a = idjumlah.text.toString().toInt()
            val b = this.tambah(a)

            idjumlah.setText(b.toString())
            idjumlahpakaian = b.toString()

            if (idjumlahtas.text.toString().toInt() == 0) {
                id_jumlahtas = "0"
            }
            if (idjumlahsepatu.text.toString().toInt() == 0) {
                id_jumlahsepatu = "0"
            }

            val c = this.Total(idjumlahpakaian!!.toInt(), id_jumlahtas!!.toInt(), id_jumlahsepatu!!.toInt())
            id_jumlah_total.text = "Rp " + c.toString()

            id_toolbar.visibility = View.VISIBLE
        }

        btn_plustas.setOnClickListener {

            val a = idjumlahtas.text.toString().toInt()
            val b = this.tambah(a)

            idjumlahtas.setText(b.toString())
            id_jumlahtas = b.toString()
            if (idjumlah.text.toString().toInt() == 0) {
                idjumlahpakaian = "0"
            }
            if (idjumlahsepatu.text.toString().toInt() == 0) {
                id_jumlahsepatu = "0"
            }

            val c = this.Total(idjumlahpakaian!!.toInt(), id_jumlahtas!!.toInt(), id_jumlahsepatu!!.toInt())
            id_jumlah_total.text = "Rp " + c.toString()

            id_toolbar.visibility = View.VISIBLE
        }

        btn_mintas.setOnClickListener {

            val a = idjumlahtas.text.toString().toInt()
            val b = this.kurang(a)

            if (b.toString() == "-1") {
                Toast.makeText(this@OutletActivity, "jumlah tidak boleh kurang dari 0", Toast.LENGTH_SHORT).show()
            } else {
                idjumlahtas.setText(b.toString())
                id_jumlahtas = b.toString()

                val c = this.Total(idjumlahpakaian!!.toInt(), id_jumlahtas!!.toInt(), id_jumlahsepatu!!.toInt())
                id_jumlah_total.text = "Rp " + c.toString()
            }
        }

        btn_plussepatu.setOnClickListener {

            val a = idjumlahsepatu.text.toString().toInt()
            val b = this.tambah(a)

            idjumlahsepatu.setText(b.toString())
            id_jumlahsepatu = b.toString()
            if (idjumlah.text.toString().toInt() == 0) {
                idjumlahpakaian = "0"
            }
            if (idjumlahtas.text.toString().toInt() == 0) {
                id_jumlahtas = "0"
            }

            val c = this.Total(idjumlahpakaian!!.toInt(), id_jumlahtas!!.toInt(), id_jumlahsepatu!!.toInt())
            id_jumlah_total.text = "Rp " + c.toString()

            id_toolbar.visibility = View.VISIBLE
        }

        btn_minsepatu.setOnClickListener {

            val a = idjumlahsepatu.text.toString().toInt()
            val b = this.kurang(a)

            if (b.toString() == "-1") {
                Toast.makeText(this@OutletActivity, "jumlah tidak boleh kurang dari 0", Toast.LENGTH_SHORT).show()
            } else {
                idjumlahsepatu.setText(b.toString())
                id_jumlahsepatu = b.toString()

                val c = this.Total(idjumlahpakaian!!.toInt(), id_jumlahtas!!.toInt(), id_jumlahsepatu!!.toInt())
                id_jumlah_total.text = "Rp " + c.toString()
            }
        }

        btn_back.setOnClickListener {
            finish()

        }
        btn_order.setOnClickListener {
            if (idjumlahpakaian.toString().equals("0") &&
                id_jumlahtas.toString().equals("0") &&
                id_jumlahsepatu.toString().equals("0")){

                Toast.makeText(this@OutletActivity, "Tidak boleh kosong" , Toast.LENGTH_SHORT).show()
            }else{
                var intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("id_laundri", id_laundri!!)
                intent.putExtra("namaLaundri", namaLaundri!!)
                intent.putExtra("id_jumpakaian", idjumlahpakaian!!)
                intent.putExtra("id_jumtas", id_jumlahtas!!)
                intent.putExtra("id_jumsepatu", id_jumlahsepatu!!)
                intent.putExtra("subpakaian", subpakaian!!)
                intent.putExtra("subtas", subtas!!)
                intent.putExtra("subsepatu", subsepatu!!)
                intent.putExtra("grandtotal", grandtotal!!)
                intent.putExtra("ongkir", ongkir!!)
                intent.putExtra("total", total!!)
                startActivity(intent)
            }
        }
    }

    fun getLaundri(id_laundri: Int) {
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        apiInterface.getLaundri(id_laundri).enqueue(object : Callback<LaundriResponse.LaundriResponse> {
            override fun onFailure(call: Call<LaundriResponse.LaundriResponse>, t: Throwable) {
                Toast.makeText(this@OutletActivity, "Gk iso", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<LaundriResponse.LaundriResponse>,
                response: Response<LaundriResponse.LaundriResponse>
            ) {
                if (response.code() == 200) {
//                    Log.e("code : ${response.code()}", "${response.body()}")
                    val laundri = response.body()!!.results.get(0)
                    tvNama.text = laundri.namaLaundri
                    tvAlamat.text = laundri.alamat

                } else {
//                    Log.e("code : ${response.code()}", response.message())
                }
            }
        })
    }

    fun kurang(a: Int): Int {
        return a - 1
    }

    fun tambah(a: Int): Int {
        return a + 1
    }

    fun Total(pakaian: Int, tas: Int, sepatu: Int): Int {
        val jum_pakaian = pakaian * 10000
        val jum_tas = tas * 12000
        val jum_sepatu = sepatu * 25000

        subpakaian = jum_pakaian.toString()
        subtas = jum_tas.toString()
        subsepatu = jum_sepatu.toString()

        val qty = pakaian + tas + sepatu
        ongkir = (qty * 2000).toString()

        val sum = jum_pakaian + jum_tas + jum_sepatu
        grandtotal = sum.toString()
        total = (grandtotal!!.toInt() + ongkir!!.toInt()).toString()
        return sum
    }
}