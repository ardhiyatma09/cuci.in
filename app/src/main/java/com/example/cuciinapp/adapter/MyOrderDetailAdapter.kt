package com.example.cuciinapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cuciinapp.R
import com.example.cuciinapp.model.MyOrderModel

class MyOrderDetailAdapter : RecyclerView.Adapter<MyOrderDetailAdapter.MyOrderDetailViewHolder> {
    lateinit var mContext : Context
    lateinit var itemMyorderDetail : List<MyOrderModel>
    var qty_jenis : String? = null
//    lateinit var listener : FirebaseDataListener

    constructor(){}
    constructor(mContext : Context, list: List<MyOrderModel>){
        this.mContext = mContext
        this.itemMyorderDetail = list
//        listener = mContext as Item1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyOrderDetailViewHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(
            R.layout.trdetail_row_activity, p0, false)
        val uploadViewHolder = MyOrderDetailViewHolder(view)
        return uploadViewHolder
    }
    override fun getItemCount(): Int {
        return itemMyorderDetail.size
    }

    override fun onBindViewHolder(p0: MyOrderDetailViewHolder, p1: Int) {
        val myorderModel : MyOrderModel = itemMyorderDetail.get(p1)

        p0.jenis.text = myorderModel.getJenis()
        p0.id_harga.text = myorderModel.getHarga()

        if (p0.jenis.text.equals("Pakaian")){
            Glide.with(mContext)
                .load(R.drawable.pakaian)
                .into(p0.img_jenis)
            qty_jenis = "kg"
        }else if(p0.jenis.text.equals("Tas")){
            Glide.with(mContext)
                .load(R.drawable.tas)
                .into(p0.img_jenis)
            qty_jenis = "pcs"
        }else if(p0.jenis.text.equals("Sepatu")){
            Glide.with(mContext)
                .load(R.drawable.sepatu)
                .into(p0.img_jenis)
            qty_jenis = "pcs"
        }

        p0.qty.text = myorderModel.getQty()+" "+qty_jenis.toString()
//        Toast.makeText(mContext, "${qty_jenis.toString()}", Toast.LENGTH_SHORT).show()

    }

    inner class MyOrderDetailViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        var ll_myorder_detail : LinearLayout
        var qty : TextView
        var jenis : TextView
        var id_harga : TextView
        var img_jenis : ImageView
        init {
            ll_myorder_detail = itemview.findViewById(R.id.ll_myorder_detail)
            qty = itemview.findViewById(R.id.qty)
            jenis = itemview.findViewById(R.id.id_jenis)
            id_harga = itemview.findViewById(R.id.id_harga)
            img_jenis = itemview.findViewById(R.id.img_jenis)
        }
    }

}