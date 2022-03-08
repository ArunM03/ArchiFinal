package com.codezcook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.login.LoginActivity
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    val firebaseAuth = FirebaseAuth.getInstance()
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,getString(R.string.appopening_interstitial_poster), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        if (firebaseAuth.currentUser != null){
            Handler().postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            },2500)
        }else{
            if (!Constants.isSubscribed) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                }
            }
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            },2500)
        }
    }
}