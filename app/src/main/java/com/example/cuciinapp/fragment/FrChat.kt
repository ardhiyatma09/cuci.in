package com.example.cuciinapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.cuciinapp.R
import com.example.cuciinapp.activity.ChatActivity
import com.example.cuciinapp.activity.ChatDetailsActivity
import com.example.cuciinapp.adapter.ChatListAdapter
import com.example.cuciinapp.data.ParseFirebaseData
import com.example.cuciinapp.data.SettingApi
import com.example.cuciinapp.model.ChatMessage
import com.example.cuciinapp.utilities.Const
import com.example.cuciinapp.widgets.DividerItemDecoration
import com.google.firebase.database.*


class FrChat : Fragment() {
    lateinit var recyclerView: RecyclerView

    private var mLayoutManager: LinearLayoutManager? = null
    var mAdapter: ChatListAdapter? = null
    private var progressBar: ProgressBar? = null

    internal lateinit var valueEventListener: ValueEventListener
    internal lateinit var ref: DatabaseReference

    internal lateinit var view: View

    internal lateinit var pfbd: ParseFirebaseData
    internal lateinit var set: SettingApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view = inflater.inflate(R.layout.fr_chat, container, false)
        pfbd = ParseFirebaseData(context!!)
        set = SettingApi(context!!)

        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar

        mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL_LIST))


        valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(Const.LOG_TAG, "Data changed from fragment")
                if (dataSnapshot.value != null)
                    mAdapter = ChatListAdapter(context!!, pfbd.getAllLastMessages(dataSnapshot))
                recyclerView.adapter = mAdapter

                mAdapter?.setOnItemClickListener(object : ChatListAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, obj: ChatMessage, position: Int) {
                        if (obj.receiver.id.equals(set.readSetting(Const.PREF_MY_ID)))
                            ChatDetailsActivity.navigate(
                                activity as ChatActivity,
                                view.findViewById(R.id.lyt_parent),
                                obj.sender
                            )
                        else if (obj.sender.id.equals(set.readSetting(Const.PREF_MY_ID)))
                            ChatDetailsActivity.navigate(
                                activity as ChatActivity,
                                view.findViewById(R.id.lyt_parent),
                                obj.receiver
                            )
                    }
                })

                bindView()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        ref = FirebaseDatabase.getInstance().getReference(Const.MESSAGE_CHILD)
        ref.addValueEventListener(valueEventListener)

        return view
    }

    fun bindView() {
        try {
            mAdapter!!.notifyDataSetChanged()
            progressBar!!.visibility = View.GONE
        } catch (e: Exception) {
        }

    }

    override fun onDestroy() {
        ref.removeEventListener(valueEventListener)
        super.onDestroy()
    }
}