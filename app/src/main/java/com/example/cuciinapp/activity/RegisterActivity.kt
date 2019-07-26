package com.example.cuciinapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.cuciinapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        val regBtn = findViewById<View>(R.id.btn_regis) as Button
        val back = findViewById<View>(R.id.back_img) as ImageView

        mDatabase = FirebaseDatabase.getInstance().getReference("Akun")

        regBtn.setOnClickListener(View.OnClickListener { view ->
            Register()
        })
        back.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

    }

    private fun Register() {
        val emailTxt = findViewById<View>(R.id.email) as EditText
        val passwordTxt = findViewById<View>(R.id.password) as EditText
        val namaTxt = findViewById<View>(R.id.nama) as EditText

        var email = emailTxt.text.toString()
        var nama = namaTxt.text.toString()
        var password = passwordTxt.text.toString()

        if (!nama.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val uid = user!!.uid
                        mDatabase.child(uid).child("ID").setValue(uid)
                        mDatabase.child(uid).child("Nama").setValue(nama)
                        mDatabase.child(uid).child("Email").setValue(email)
                        Toast.makeText(this, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "tolong isi dengan lengkap", Toast.LENGTH_SHORT).show()
        }
    }
}