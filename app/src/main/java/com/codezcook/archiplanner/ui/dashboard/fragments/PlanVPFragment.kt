package com.codezcook.archiplanner.ui.dashboard.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.adapter.FragmentAdapter
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import kotlinx.android.synthetic.main.fragment_elevation.*
import kotlinx.android.synthetic.main.fragment_planlist.*
import kotlinx.android.synthetic.main.fragment_planvp.*

class PlanVPFragment : Fragment(R.layout.fragment_planvp) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FragmentAdapter(childFragmentManager,lifecycle,Constants.planlist.size)
        vp_plans.adapter = adapter
        vp_plans.setCurrentItem(Constants.curPlanPos,false)
        Constants.curPlanResponseItem = Constants.planlist[Constants.curPlanPos]

        setBannerAd()

       // Toast.makeText(requireContext(),"curplanpos ${Constants.curPlanPos}",Toast.LENGTH_SHORT).show()


            vp_plans.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Constants.curPlanResponseItem = Constants.planlist[position]
                    Constants.curPlanPos = position
                    super.onPageSelected(position)
                }
            })

    }

    private fun setBannerAd(){
       if (!Constants.isSubscribed) {
        //   Toast.makeText(requireContext(),"Function Called",Toast.LENGTH_LONG).show()
           val adRequest = AdRequest.Builder().build()
            banner_download.loadAd(adRequest)
        }else {
           banner_download.visibility = View.GONE
       }
        banner_download.adListener = object: AdListener() {
            override fun onAdLoaded() {
      //   Toast.makeText(requireContext(),"Ad Loaded",Toast.LENGTH_LONG).show()
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
         //       Toast.makeText(requireContext(),"Ad Error ${adError.message}",Toast.LENGTH_LONG).show()
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }
}