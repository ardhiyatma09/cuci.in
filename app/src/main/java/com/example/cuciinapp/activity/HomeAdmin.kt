package com.example.cuciinapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.cuciinapp.R
import com.example.cuciinapp.adapter.AdminOrderAdapter
import com.example.cuciinapp.model.MyOrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeAdmin : AppCompatActivity() {

    private var adminorderAdapter: AdminOrderAdapter? = null
    private var rvadminorder: RecyclerView? = null
    private var list: MutableList<MyOrderModel> = ArrayList<MyOrderModel>()
    lateinit var dbref: DatabaseReference
    lateinit var helperPrefs: PrefsHelper
    lateinit var fAuth: FirebaseAuth

    var id_laundri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeadmin_activity)
        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
        setSupportActionBar(toolbar)
        toolbar.title = "Cuci.in Admin"
        toolbar.setLogo(R.mipmap.ic_logo)
        helperPrefs = PrefsHelper(this)
        rvadminorder = findViewById(R.id.rvAdminorder)
        rvadminorder!!.layoutManager = LinearLayoutManager(this)
        rvadminorder!!.setHasFixedSize(true)
        fAuth = FirebaseAuth.getInstance()

        getDataAdmin()
//        Toast.makeText(this@HomeAdmin, "${id_laundri.toString()}", Toast.LENGTH_SHORT).show()
//        getDataOrder(id_laundri!!.toString())

    }

    fun getDataAdmin() {
        val dbRefUser = FirebaseDatabase.getInstance().getReference("Akun/${helperPrefs.getUI()}")
        dbRefUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("uid", helperPrefs.getUI())
                id_laundri = p0.child("/id_laundri").value.toString()
                getDataOrder(id_laundri!!.toString())
//                Toast.makeText(this@HomeAdmin, "${id_laundri.toString()}", Toast.LENGTH_SHORT).show()
//                Log.e("lol", "${p0}")
            }

        })
    }

    fun getDataOrder(id_laundri: String) {
        dbref = FirebaseDatabase.getInstance().getReference("Transaksi")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                list = ArrayList<MyOrderModel>()
                for (dataSnapshot in p0.children) {
                    val addDataAll = dataSnapshot.getValue(MyOrderModel::class.java)
                    if (addDataAll!!.getId_laundri() == id_laundri.toLong()) {
                        addDataAll.setKey(dataSnapshot.key!!)
                        list.add(addDataAll)
                        adminorderAdapter = AdminOrderAdapter(this@HomeAdmin, list)
                        rvadminorder!!.adapter = adminorderAdapter
                    }
//                    Log.e("TAG_ERROR", "${list}")
//                    Log.e("view", "${dataSnapshot}")
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("TAG_ERROR", p0.message)
            }

        })
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