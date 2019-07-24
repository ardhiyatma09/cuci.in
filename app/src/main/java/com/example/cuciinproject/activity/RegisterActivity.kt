package com.example.cuciinproject.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.cuciinproject.R

class RegisterActivity : AppCompatActivity() {

    lateinit var arrowleft : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        arrowleft = findViewById(R.id.arrowleft)

        arrowleft.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}