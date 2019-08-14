package com.example.cuciinapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.cuciinapp.R
import com.example.cuciinapp.adapter.MyOrderDetailAdapter
import com.example.cuciinapp.model.MyOrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.trdetail_activity.*

class TrDetailActivity : AppCompatActivity() {

    private var myorderdetailAdapter : MyOrderDetailAdapter? = null
    private var rvMyorderDetail : RecyclerView? = null
    private var list : MutableList<MyOrderModel> = ArrayList<MyOrderModel>()
    lateinit var dbref : DatabaseReference
    lateinit var fAuth: FirebaseAuth
    lateinit var helperPrefs: PrefsHelper

    var idtransaksi : String? = null
    var namaLaundri : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trdetail_activity)

        fAuth = FirebaseAuth.getInstance()
        helperPrefs = PrefsHelper(this)
        rvMyorderDetail = findViewById(R.id.rvMyorderDetail)
        rvMyorderDetail!!.layoutManager = LinearLayoutManager(this)
        rvMyorderDetail!!.setHasFixedSize(true)


        idtransaksi = intent.getLongExtra("idtransaksi", 0).toString()
//        namaLaundri = intent.getStringExtra("namaLaundri")


//        id_trans.setText("Transaksi "+idtransaksi.toString())
//        nama_laundri.setText(namaLaundri.toString())
        getDataUser()
        getDataTransaksi(idtransaksi!!.toLong())
        getDataDetail()

        btn_back_myorder.setOnClickListener {
            finish()
        }
    }

    fun getDataUser(){
        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${helperPrefs.getUI()}")
        dbRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
//                Log.e("uid", helperPrefs.getUI())
                nama_user.text = p0.child("/Nama").value.toString()
                tr_alamat.text = p0.child("/Alamat").value.toString()
//                view.nama.text = p0.child("/Nama").value.toString()
//                view.alamat.text = p0.child("/Alamat").value.toString()
//                view.kontak.text = p0.child("/Kontak").value.toString()
//                view.email.text = p0.child("/Email").value.toString()
            }

        })
    }
    fun getDataTransaksi(id_transaksi : Long){
        val dbRefUser = FirebaseDatabase.getInstance().getReference("Transaksi/${id_transaksi}")
        dbRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                id_trans.text = "Transaksi "+p0.child("/id_transaksi").value.toString()
                nama_laundri.text = p0.child("/nama_laundri").value.toString()
                subtotal_detail.text = "Rp. "+p0.child("/subtotal").value.toString()
                sub_ongkir.text = "Rp. "+p0.child("/ongkir").value.toString()
                total_my.text = "Rp. "+p0.child("/total").value.toString()
                status.text = p0.child("/status").value.toString()

            }

        })
    }
    fun getDataDetail(){
        dbref = FirebaseDatabase.getInstance().getReference("Detail_Transaksi")
        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                list = ArrayList<MyOrderModel>()
                for (dataSnapshot in p0.children){
                    val addDataAll = dataSnapshot.getValue(MyOrderModel::class.java)
                    if (addDataAll!!.getId_transaksi()!!.equals(idtransaksi!!.toLong())){
                        addDataAll!!.setKey(dataSnapshot.key!!)
                        list.add(addDataAll!!)
                        myorderdetailAdapter= MyOrderDetailAdapter(this@TrDetailActivity, list)
                        rvMyorderDetail!!.adapter = myorderdetailAdapter
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("TAG_ERROR", p0.message)
            }

        })
    }
}