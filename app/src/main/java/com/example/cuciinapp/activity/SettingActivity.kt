package com.example.cuciinapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.cuciinapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.ubah_activity.*

class SettingActivity : AppCompatActivity() {

    lateinit var fAuth: FirebaseAuth
    lateinit var helperPrefs: PrefsHelper
    lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubah_activity)

        fAuth = FirebaseAuth.getInstance()
        helperPrefs = PrefsHelper(this)


        FirebaseDatabase.getInstance().getReference("Akun/${fAuth.uid}")
            .child("Nama").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    nama_update.setText(p0.value.toString())
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })

        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${helperPrefs.getUI()}")
        dbRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("uid", helperPrefs.getUI())
                nama_update.setText(p0.child("/Nama").value.toString())
                alamat_update.setText(p0.child("/Alamat").value.toString())
                kontak_update.setText(p0.child("/Kontak").value.toString())
                email_update.setText(p0.child("/Email").value.toString())
            }
        })

        btn_update.setOnClickListener {
            val uidUser = fAuth.currentUser?.uid
            dbRef = FirebaseDatabase.getInstance().reference
            val update_nama = nama_update.text.toString()
            val update_alamat = alamat_update.text.toString()
            val update_kontak = kontak_update.text.toString()
            val update_email = email_update.text.toString()


            dbRef.child("Akun/$uidUser/Nama").setValue(update_nama)
            dbRef.child("Akun/$uidUser/Alamat").setValue(update_alamat)
            dbRef.child("Akun/$uidUser/Kontak").setValue(update_kontak)
            dbRef.child("Akun/$uidUser/Email").setValue(update_email)
            Toast.makeText(this, "Sukses!!", Toast.LENGTH_SHORT).show()
//            val refresh = Intent(this@SettingActivity, FrProfile::class.java)
//            startActivity(refresh)
            finish()

        }
    }

}