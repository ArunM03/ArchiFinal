package com.codezcook.archiplanner.ui.dashboard.elevation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_elevation.*
import kotlinx.android.synthetic.main.fragment_plan.*

class ElevationFragment : Fragment(R.layout.fragment_elevation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setBannerAd()


        cd_ground.setOnClickListener {
            Constants.curElevation = Constants.GROUNDELEVATION
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_elevationFragment_to_elevationListFragment)
        }
        cd_first.setOnClickListener {
            Constants.curElevation = Constants.FIRSTELEVATION
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_elevationFragment_to_elevationListFragment)
        }
        cd_second.setOnClickListener {
            Constants.curElevation = Constants.SECONDELEVATION
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_elevationFragment_to_elevationListFragment)
        }
        cd_third.setOnClickListener {
            Constants.curElevation = Constants.THIRDELEVATION
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_elevationFragment_to_elevationListFragment)
        }
        cd_fourth.setOnClickListener {
            Constants.curElevation = Constants.FOURTHELEVATION
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_elevationFragment_to_elevationListFragment)
        }


    }


    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_elevation.loadAd(adRequest)
        } else {
            banner_elevation.visibility = View.GONE
        }
    }


}