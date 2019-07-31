package com.example.cuciinapp.item

import android.support.v4.app.FragmentManager
import com.example.cuciinapp.R
import com.example.cuciinapp.adapter.BannerAdapter
import com.example.cuciinapp.model.Banner
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_carousel_banner.view.*


class BannerCarouselItem(
    private val banners: List<Banner>,
    private val supportFragmentManager: FragmentManager
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val viewPagerAdapter = BannerAdapter(supportFragmentManager, banners)
        viewHolder.itemView.viewPagerBanner.adapter = viewPagerAdapter
        viewHolder.itemView.indicator.setViewPager(viewHolder.itemView.viewPagerBanner)

    }

    override fun getLayout(): Int = R.layout.item_carousel_banner
}