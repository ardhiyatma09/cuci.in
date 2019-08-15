package com.example.cuciinapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.cuciinapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class HomeAdmin : AppCompatActivity() {

    lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeadmin_activity)

        fAuth = FirebaseAuth.getInstance()

        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
        setSupportActionBar(toolbar)
        toolbar.title = "Cuci.in Admin"
        toolbar.setLogo(R.mipmap.ic_logo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_chat -> {
            startActivity(Intent(this, ChatActivity::class.java))
            true
        }
        R.id.action_signout -> {
            signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            true
        }


        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        val user = fAuth.currentUser
        if (user == null) {
            finish()
        }
    }

    fun signOut() {
        fAuth.signOut()

    }
}