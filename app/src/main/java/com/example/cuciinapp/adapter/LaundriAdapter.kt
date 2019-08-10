package com.example.cuciinapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cuciinapp.R
import com.example.cuciinapp.activity.OutletActivity
import com.example.cuciinapp.model.Laundri.Laundri
import kotlinx.android.synthetic.main.row_layout.view.*

//import com.example.tutorial_retrofit_mysql.R
//import com.example.tutorial_retrofit_mysql.model.Laundri.Laundri

class LaundriAdapter(val laundri: ArrayList<Laundri>) : RecyclerView.Adapter<LaundriAdapter.MovieViewHolder>() {

    lateinit var mContext: Context

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(laundri.get(position))

    }

    override fun getItemCount() = laundri.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private var laundri: Laundri? = null

        override fun onClick(p0: View?) {
            var id_laundri = laundri!!.id_laundri
//            val b = Bundle()
//            b.putSerializable("kode", datax)
            var intent = Intent(view.context, OutletActivity::class.java)
            intent.putExtra("id_laundri", id_laundri!!)
            view.context.startActivity(intent)

//            view.context.startActivity(Intent(view.context, OutletActivity::class.java))
        }

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(laundri: Laundri) {
            this.laundri = laundri
//            val imageUrl = StringBuilder()
            val urlGambarBerita = "http://172.168.10.7/cuci_in/images/" + laundri.foto
            Glide.with(view.context)
                .load(urlGambarBerita)
                .into(view.mvPoster)
//            imageUrl.append(view.context.getString(R.string.base_path_poster)).append(laundri.posterPath)
            view.mvTitle.text = laundri.namaLaundri
//            Glide.with(view.context).load(imageUrl.toString()).into(view.mvPoster)
        }
    }
}