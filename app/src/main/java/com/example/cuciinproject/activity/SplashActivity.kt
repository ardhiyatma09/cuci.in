package com.example.cuciinproject.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.cuciinproject.R

class SplashActivity : AppCompatActivity() {

    lateinit var tv_splash : TextView
    lateinit var logo : ImageView
    lateinit var animbot : Animation
    lateinit var animtop : Animation

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 4000 //4 seconds
    internal val mRunnable: Runnable = Runnable {

        if (!isFinishing) {

            val intent = Intent(applicationContext, LoginActivity::class.java)

            startActivity(intent)

            finish()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        logo = findViewById(R.id.logosplash)
        tv_splash = findViewById(R.id.tv_splash)
        animbot = AnimationUtils.loadAnimation(this, R.anim.frombottom)
        animtop = AnimationUtils.loadAnimation(this, R.anim.fromtop)

        logo.startAnimation(animtop)
        tv_splash.startAnimation(animbot)

        //Initialize the Handler

        mDelayHandler = Handler()

        //Navigate with delay

        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {

            mDelayHandler!!.removeCallbacks(mRunnable)

        }

        super.onDestroy()

    }
}