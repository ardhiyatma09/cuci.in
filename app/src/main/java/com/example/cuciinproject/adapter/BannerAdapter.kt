package com.example.cuciinproject.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.cuciinproject.fragment.FrBanner
import com.example.cuciinproject.model.Banner

class BannerAdapter(
    fragmentManager: FragmentManager,
    private val banners: List<Banner>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment {
        return FrBanner.newInstance(banners[pos].image)
    }

    override fun getCount(): Int = banners.size

}