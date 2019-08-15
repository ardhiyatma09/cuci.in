package com.example.cuciinapp.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log.e
import android.view.View
import com.example.cuciinapp.R
import com.example.cuciinapp.adapter.FriendListAdapter
import com.example.cuciinapp.data.ParseFirebaseData
import com.example.cuciinapp.model.Friend
import com.example.cuciinapp.utilities.CustomToast
import com.example.cuciinapp.widgets.DividerItemDecoration
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class SelectFriendActivity : AppCompatActivity() {
    private var actionBar: ActionBar? = null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: FriendListAdapter? = null
    internal lateinit var friendList: List<Friend>

    val USERS_CHILD = "Akun"
    internal lateinit var pfbd: ParseFirebaseData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)
        initToolbar()
        initComponent()
        friendList = ArrayList<Friend>()
        pfbd = ParseFirebaseData(this)

        val ref = FirebaseDatabase.getInstance().getReference(USERS_CHILD)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mAdapter = FriendListAdapter(this@SelectFriendActivity, pfbd.getAllUser(dataSnapshot))
                recyclerView!!.adapter = mAdapter

                mAdapter!!.setOnItemClickListener(object : FriendListAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, obj: Friend, position: Int) {
                        ChatDetailsActivity.navigate(this@SelectFriendActivity, findViewById(R.id.lyt_parent), obj)
                    }
                })

                bindView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                CustomToast(this@SelectFriendActivity).showError(getString(R.string.error_could_not_connect))
                e("DATAEROOR", databaseError.toString())
            }
        })

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Tools.systemBarLolipop(this)
//        }
    }

    private fun initComponent() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val mLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))
    }

    fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeButtonEnabled(true)
    }

    fun bindView() {
        try {
            mAdapter!!.notifyDataSetChanged()
        } catch (e: Exception) {
        }

    }
}