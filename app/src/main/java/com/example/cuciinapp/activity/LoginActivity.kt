package com.example.cuciinapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log.e
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cuciinapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    lateinit var helperPrefs: PrefsHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        helperPrefs = PrefsHelper(this)
        val loginBtn = findViewById<View>(R.id.btn_login) as Button
        val regisTxt = findViewById<View>(R.id.toRegister) as TextView

        loginBtn.setOnClickListener(View.OnClickListener { View ->
            login()
        })

        regisTxt.setOnClickListener { View ->
            register()
        }

    }

    private fun login() {
        val emailTxt = findViewById<View>(R.id.email) as EditText
        val passwordTxt = findViewById<View>(R.id.password) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        //admin@cuci.in
        //admin[0]
        //cuci.in[1]

            //admin@cuci.in
            //admin[0]
            //cuci.in[1]

            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
//                    startActivity(Intent(this, MainActivity::class.java))
                            val user = mAuth.currentUser
                            if (email.split("@")[1].equals("cuciin.com")) {
                                helperPrefs.saveUID(user!!.uid) //berfungsi untuk save uid ke sharedpreferences
                                helperPrefs.saveStatus("Admin")
                                startActivity(Intent(this, HomeAdmin::class.java))
                                Toast.makeText(this, "Berhasil login ! Admin", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                updateUI(user)
                                Toast.makeText(this, "Berhasil login", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Toast.makeText(this, "Password Salah!", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Isi Form dengan lengkap!!", Toast.LENGTH_SHORT).show()
            }

    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            helperPrefs.saveUID(user.uid) //berfungsi untuk save uid ke sharedpreferences
            helperPrefs.saveStatus("User")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            e("TAG_ERROR", "user tidak ada")
        }
    }


    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            updateUI(user)
        }
    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}