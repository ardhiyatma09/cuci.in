package com.example.cuciinapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.cuciinapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.order_activity.*

class OrderActivity : AppCompatActivity() {


    lateinit var fAuth: FirebaseAuth
    lateinit var dbRef: DatabaseReference
    lateinit var helperPref: PrefsHelper

    var id_transaksi: Int? = null
    var id_detail: Int? = null
    var id_laundri: Int? = null
    var namaLaundri: String? = null
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
        setContentView(R.layout.order_activity)

        helperPref = PrefsHelper(this)
        fAuth = FirebaseAuth.getInstance()

        getDataAdmin()

        id_laundri = intent.getIntExtra("id_laundri", 0)
        namaLaundri = intent.getStringExtra("namaLaundri")
        idjumlahpakaian = intent.getStringExtra("id_jumpakaian")
        id_jumlahtas = intent.getStringExtra("id_jumtas")
        id_jumlahsepatu = intent.getStringExtra("id_jumsepatu")
        subpakaian = intent.getStringExtra("subpakaian")
        subtas = intent.getStringExtra("subtas")
        subsepatu = intent.getStringExtra("subsepatu")
        grandtotal = intent.getStringExtra("grandtotal")
        ongkir = intent.getStringExtra("ongkir")
        total = intent.getStringExtra("total")


        p_idjumlah.text = idjumlahpakaian.toString() + " kg"
        t_idjumlah.text = id_jumlahtas.toString() + " pcs"
        s_idjumlah.text = id_jumlahsepatu.toString() + " pcs"

        p_idjumlah_2.text = idjumlahpakaian.toString() + " kg"
        t_idjumlah_2.text = id_jumlahtas.toString() + " pcs"
        s_idjumlah_2.text = id_jumlahsepatu.toString() + " pcs"

        p_subtotal.text = "Rp. " + subpakaian.toString()
        t_subtotal.text = "Rp. " + subtas.toString()
        s_subtotal.text = "Rp. " + subsepatu.toString()

        p_subtotal_2.text = "Rp. " + subpakaian.toString()
        t_subtotal_2.text = "Rp. " + subtas.toString()
        s_subtotal_2.text = "Rp. " + subsepatu.toString()

        tvSubtotal.text = "Rp. " + grandtotal.toString()
        tvTotalKirim.text = "Rp. " + ongkir.toString()
        tvTotal.text = "Rp. " + total.toString()

        if (idjumlahpakaian.toString().equals("0")) {
            p_grub.visibility = View.GONE
            p_grub_2.visibility = View.GONE
        } else {
        }
        if (id_jumlahtas.toString().equals("0")) {
            t_grub.visibility = View.GONE
            t_grub_2.visibility = View.GONE
        } else {
        }
        if (id_jumlahsepatu.toString().equals("0")) {
            s_grub.visibility = View.GONE
            s_grub_2.visibility = View.GONE
        } else {
        }

        btn_back_order.setOnClickListener {
            finish()
        }
        id_make_order.setOnClickListener {
            simpanTransaksi()
            if (idjumlahpakaian.toString().equals("0")) {
            } else {
                simpanPakaian()
            }
            if (id_jumlahtas.toString().equals("0")) {
            } else {
                simpanTas()
            }
            if (id_jumlahsepatu.toString().equals("0")) {
            } else {
                simpanSepatu()
            }
            Toast.makeText(
                this@OrderActivity, "Data berhasil ditambah",
                Toast.LENGTH_SHORT
            ).show()
//            onBackPressed()
            startActivity(Intent(this@OrderActivity, MainActivity::class.java))
            finish()
        }

    }

    fun getDataAdmin() {
        val uidUser = helperPref.getUI()
        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${uidUser}")
        dbRefUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("uid", helperPref.getUI())
                et_alamat.setText(p0.child("/Alamat").value.toString())
//                Toast.makeText(this@HomeAdmin, "${id_laundri.toString()}", Toast.LENGTH_SHORT).show()
//                Log.e("lol", "${p0}")
            }

        })
    }

    fun simpanTransaksi() {
        val uidUser = helperPref.getUI()
        val CounterId = helperPref.getCounterId()
        id_transaksi = helperPref.getCounterId()
        val alamat = et_alamat.text.toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Transaksi/${id_transaksi!!.toInt()}")
        dbRef.child("id_transaksi").setValue(id_transaksi!!.toInt())
        dbRef.child("id_laundri").setValue(id_laundri!!.toInt())
        dbRef.child("nama_laundri").setValue(namaLaundri!!.toString())
        dbRef.child("id_user").setValue(uidUser)
        dbRef.child("status").setValue("Konfirmasi")
        dbRef.child("alamat").setValue(alamat!!.toString())
        dbRef.child("subtotal").setValue(grandtotal.toString())
        dbRef.child("ongkir").setValue(ongkir.toString())
        dbRef.child("total").setValue(total.toString())

        helperPref.saveCounterId(CounterId + 1)
    }

    fun simpanPakaian() {
        var CounterDetailId = helperPref.getCounterDetailId()
        id_detail = helperPref.getCounterDetailId()
        val user = fAuth.currentUser!!
        dbRef = FirebaseDatabase.getInstance().getReference("Detail_Transaksi/${id_detail!!.toInt()}")
        dbRef.child("id_detail").setValue(id_detail!!.toInt())
        dbRef.child("id_transaksi").setValue(id_transaksi!!.toInt())
        dbRef.child("jenis").setValue("Pakaian")
        dbRef.child("qty").setValue(idjumlahpakaian.toString())
        dbRef.child("harga").setValue(subpakaian.toString())

        helperPref.saveCounterDetail(CounterDetailId + 1)
    }

    fun simpanTas() {
        var CounterDetailId = helperPref.getCounterDetailId()
        id_detail = helperPref.getCounterDetailId()
        val user = fAuth.currentUser!!
        dbRef = FirebaseDatabase.getInstance().getReference("Detail_Transaksi/${id_detail!!.toInt()}")
        dbRef.child("id_detail").setValue(id_detail!!.toInt())
        dbRef.child("id_transaksi").setValue(id_transaksi!!.toInt())
        dbRef.child("jenis").setValue("Tas")
        dbRef.child("qty").setValue(id_jumlahtas.toString())
        dbRef.child("harga").setValue(subtas.toString())

        helperPref.saveCounterDetail(CounterDetailId + 1)
    }

    fun simpanSepatu() {
        var CounterDetailId = helperPref.getCounterDetailId()
        id_detail = helperPref.getCounterDetailId()
        val user = fAuth.currentUser!!
        dbRef = FirebaseDatabase.getInstance().getReference("Detail_Transaksi/${id_detail!!.toInt()}")
        dbRef.child("id_detail").setValue(id_detail!!.toInt())
        dbRef.child("id_transaksi").setValue(id_transaksi!!.toInt())
        dbRef.child("jenis").setValue("Sepatu")
        dbRef.child("qty").setValue(id_jumlahsepatu.toString())
        dbRef.child("harga").setValue(subsepatu.toString())

        helperPref.saveCounterDetail(CounterDetailId + 1)
    }
}