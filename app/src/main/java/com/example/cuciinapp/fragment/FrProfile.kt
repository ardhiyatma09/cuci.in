package com.example.cuciinapp.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuciinapp.R
import com.example.cuciinapp.activity.LoginActivity
import com.example.cuciinapp.activity.PrefsHelper
import com.example.cuciinapp.activity.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fr_profile_activity.*
import kotlinx.android.synthetic.main.fr_profile_activity.view.*

class FrProfile : Fragment() {

    lateinit var fAuth: FirebaseAuth
    lateinit var helperPrefs: PrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_profile_activity, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fAuth = FirebaseAuth.getInstance()
        helperPrefs = PrefsHelper(activity!!)
//        nama.setText(fUser)

        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${helperPrefs.getUI()}")
        dbRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("uid", helperPrefs.getUI())
                view.nama.text = p0.child("/Nama").value.toString()
                view.alamat.text = p0.child("/Alamat").value.toString()
                view.kontak.text = p0.child("/Kontak").value.toString()
                view.email.text = p0.child("/Email").value.toString()
            }

        })


        btn_logout.setOnClickListener {
            signOut()
            startActivity(Intent(activity!!, LoginActivity::class.java))
            activity!!.finish()
        }

        ubah.setOnClickListener {
            startActivity(Intent(activity!!, SettingActivity::class.java))
        }
    }

    fun signOut() {
        fAuth.signOut()

    }
}