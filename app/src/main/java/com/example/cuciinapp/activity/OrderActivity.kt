package com.example.cuciinapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.cuciinapp.R
import kotlinx.android.synthetic.main.order_activity.*

class OrderActivity: AppCompatActivity() {

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

        idjumlahpakaian = intent.getStringExtra("id_jumpakaian")
        id_jumlahtas = intent.getStringExtra("id_jumtas")
        id_jumlahsepatu = intent.getStringExtra("id_jumsepatu")
        subpakaian = intent.getStringExtra("subpakaian")
        subtas = intent.getStringExtra("subtas")
        subsepatu= intent.getStringExtra("subsepatu")
        grandtotal= intent.getStringExtra("grandtotal")
        ongkir= intent.getStringExtra("ongkir")
        total= intent.getStringExtra("total")


        p_idjumlah.setText(idjumlahpakaian.toString()+" kg")
        t_idjumlah.setText(id_jumlahtas.toString()+" pcs")
        s_idjumlah.setText(id_jumlahsepatu.toString()+" pcs")

        p_idjumlah_2.setText(idjumlahpakaian.toString()+" kg")
        t_idjumlah_2.setText(id_jumlahtas.toString()+" pcs")
        s_idjumlah_2.setText(id_jumlahsepatu.toString()+" pcs")

        p_subtotal.setText("Rp. "+subpakaian.toString())
        t_subtotal.setText("Rp. "+subtas.toString())
        s_subtotal.setText("Rp. "+subsepatu.toString())

        p_subtotal_2.setText("Rp. "+subpakaian.toString())
        t_subtotal_2.setText("Rp. "+subtas.toString())
        s_subtotal_2.setText("Rp. "+subsepatu.toString())

        tvSubtotal.setText("Rp. "+grandtotal.toString())
        tvTotalKirim.setText("Rp. "+ongkir.toString())
        tvTotal.setText("Rp. "+total.toString())

        if (idjumlahpakaian.toString().equals("0")){
            p_grub.visibility = View.GONE
            p_grub_2.visibility = View.GONE
        }else{
        }
        if (id_jumlahtas.toString().equals("0")){
            t_grub.visibility = View.GONE
            t_grub_2.visibility = View.GONE
        }else{
        }
        if (id_jumlahsepatu.toString().equals("0")){
            s_grub.visibility = View.GONE
            s_grub_2.visibility = View.GONE
        }else{
        }

        btn_back_order.setOnClickListener {
            finish()
        }
    }
}