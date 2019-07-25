package com.example.cuciinproject.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cuciinproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fr_banner.imgBanner

class FrBanner : Fragment() {

    companion object {
        /**
         * new instance pattern for fragment
         */
        @JvmStatic
        fun newInstance(url: String): FrBanner {
            val newsFragment = FrBanner()
            val args = Bundle()
            args.putString("img", url)
            newsFragment.arguments = args
            return newsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fr_banner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("img")

        url.let {
            Picasso.get().load(url).into(imgBanner)
        }
    }


}