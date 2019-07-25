package com.example.cuciinproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuciinproject.R
import com.example.cuciinproject.item.BannerCarouselItem
import com.example.cuciinproject.model.Banner
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fr_homeuser_activity.*

class FrHomeUser : Fragment() {

    private var groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fr_homeuser_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val promos = listOf(
            Banner(
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            Banner(

                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            Banner(
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            Banner(

                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            Banner(
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            Banner(

                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            )
        )

        rvMain.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = groupAdapter
        }

        // declare banner carousel
        val bannerCarouselItem = BannerCarouselItem(promos, childFragmentManager)


        groupAdapter.add(bannerCarouselItem)
    }
}