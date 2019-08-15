package com.example.cuciinapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuciinapp.R
import kotlinx.android.synthetic.main.fr_myorder_activity.*
import com.example.cuciinapp.activity.PrefsHelper
import com.example.cuciinapp.adapter.MyOrderAdapter
import com.example.cuciinapp.model.MyOrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FrMyorder : Fragment() {

    private var myorderAdapter : MyOrderAdapter? = null
    private var rvMyorder : RecyclerView? = null
    private var list : MutableList<MyOrderModel> = ArrayList<MyOrderModel>()
    lateinit var dbref : DatabaseReference
    lateinit var helperPrefs: PrefsHelper
    lateinit var fAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fr_myorder_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helperPrefs = PrefsHelper(activity!!)
        rvMyorder = view.findViewById(R.id.rvMyorder)
        rvMyorder!!.layoutManager = LinearLayoutManager(activity!!)
        rvMyorder!!.setHasFixedSize(true)
        fAuth = FirebaseAuth.getInstance()

        val uidUser = helperPrefs.getUI()

        dbref = FirebaseDatabase.getInstance().getReference("Transaksi")
        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                list = ArrayList<MyOrderModel>()
                for (dataSnapshot in p0.children){
                    val addDataAll = dataSnapshot.getValue(MyOrderModel::class.java)
                    if (addDataAll!!.getId_user() == uidUser){
                    addDataAll!!.setKey(dataSnapshot.key!!)
                    list.add(addDataAll!!)
                    myorderAdapter = MyOrderAdapter(activity!!.applicationContext, list)
                    rvMyorder!!.adapter = myorderAdapter
                    }
//                    Log.e("TAG_ERROR", "${list}")
                    Log.e("view", "${dataSnapshot}")
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("TAG_ERROR", p0.message)
            }

        })
    }

}