package com.example.cuciinapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.cuciinapp.R
import com.example.cuciinapp.activity.OrderActivity
import com.example.cuciinapp.activity.TrDetailActivity
import com.example.cuciinapp.model.MyOrderModel

class MyOrderAdapter : RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {
    lateinit var mContext : Context
    lateinit var itemMyorder : List<MyOrderModel>
//    lateinit var listener : FirebaseDataListener

    constructor(){}
    constructor(mContext : Context, list: List<MyOrderModel>){
        this.mContext = mContext
        this.itemMyorder = list
//        listener = mContext as Item1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyOrderViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(
            R.layout.myorder_activity, p0, false)
        val uploadViewHolder = MyOrderViewHolder(view)
        return uploadViewHolder
    }
    override fun getItemCount(): Int {
        return itemMyorder.size
    }

    override fun onBindViewHolder(p0: MyOrderViewHolder, p1: Int) {
        val myorderModel : MyOrderModel = itemMyorder.get(p1)
        val idtrans = myorderModel.getId_transaksi().toString()
        p0.idtransaksi.text = "Transaksi "+idtrans
        p0.idlaundri.text = myorderModel.getNama_laundri()
        p0.status.text = myorderModel.getStatus()
        p0.trTotal.text = "Rp. "+myorderModel.getTotal()


        p0.ll_myorder.setOnClickListener {
//            Toast.makeText(mContext, "${p0.idtransaksi.text}/${p0.idlaundri.text}",
//                Toast.LENGTH_SHORT).show()
            var idtransaksi = idtrans.toLong()
            var namaLaundri = myorderModel.getNama_laundri()
//            val b = Bundle()
//            b.putSerializable("kode", datax)
            var intent = Intent(mContext, TrDetailActivity::class.java)
            intent.putExtra("idtransaksi", idtransaksi!!)
            intent.putExtra("namaLaundri", namaLaundri!!)
            mContext.startActivity(intent)
        }



//            view.context.startActivity(Intent(view.context, OutletActivity::class.java))
    }

    inner class MyOrderViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        var ll_myorder : LinearLayout
        var idtransaksi : TextView
        var idlaundri : TextView
        var status : TextView
        var trTotal : TextView
        init {
            ll_myorder = itemview.findViewById(R.id.ll_myorder)
            idtransaksi = itemview.findViewById(R.id.id_transaksi)
            idlaundri = itemview.findViewById(R.id.id_laundri)
            status = itemview.findViewById(R.id.status)
            trTotal = itemview.findViewById(R.id.trTotal)
        }
    }

}