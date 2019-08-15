package com.example.cuciinapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.cuciinapp.R
import com.example.cuciinapp.activity.TrDetailActivity
import com.example.cuciinapp.model.MyOrderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminOrderAdapter : RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder> {
    lateinit var mContext : Context
    lateinit var itemMyorder : List<MyOrderModel>
//    lateinit var listener : FirebaseDataListener

    constructor(){}
    constructor(mContext : Context, list: List<MyOrderModel>){
        this.mContext = mContext
        this.itemMyorder = list
//        listener = mContext as Item1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdminOrderViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(
            R.layout.homeadmin_row_activity, p0, false)
        val uploadViewHolder = AdminOrderViewHolder(view)
        return uploadViewHolder
    }
    override fun getItemCount(): Int {
        return itemMyorder.size
    }

    override fun onBindViewHolder(p0: AdminOrderViewHolder, p1: Int) {
        val myorderModel : MyOrderModel = itemMyorder.get(p1)
        val idtrans = myorderModel.getId_transaksi().toString()
        p0.idtransaksi.text = "Transaksi "+idtrans
        p0.idlaundri.text = myorderModel.getNama_laundri()
        p0.status.text = myorderModel.getStatus()
        p0.trTotal.text = "Rp. "+myorderModel.getTotal()

        val id_user = myorderModel.getId_user().toString()
//        Toast.makeText(mContext.applicationContext, id_user, Toast.LENGTH_SHORT).show()
        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${id_user}")
        dbRefUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p3: DatabaseError) {

            }

            override fun onDataChange(p3: DataSnapshot) {
                var nama_user = p3.child("/Nama").value.toString()
                p0.namauser.text = nama_user
            }

        })

        p0.ll_adminorder.setOnClickListener {
            //            Toast.makeText(mContext, "${p0.idtransaksi.text}/${p0.idlaundri.text}",
//                Toast.LENGTH_SHORT).show()
            var idtransaksi = idtrans.toLong()
//            val b = Bundle()
//            b.putSerializable("kode", datax)
            var intent = Intent(mContext, TrDetailActivity::class.java)
            intent.putExtra("idtransaksi", idtransaksi!!)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.applicationContext.startActivity(intent)
        }



//            view.context.startActivity(Intent(view.context, OutletActivity::class.java))
    }

    inner class AdminOrderViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        var ll_adminorder : LinearLayout
        var namauser : TextView
        var idtransaksi : TextView
        var idlaundri : TextView
        var status : TextView
        var trTotal : TextView
        init {
            ll_adminorder = itemview.findViewById(R.id.ll_row_admin)
            namauser = itemview.findViewById(R.id.mvUser)
            idtransaksi = itemview.findViewById(R.id.mvTransaksi)
            idlaundri = itemview.findViewById(R.id.mvLaundri)
            status = itemview.findViewById(R.id.mvStatus)
            trTotal = itemview.findViewById(R.id.mvTotal)
        }
    }
}