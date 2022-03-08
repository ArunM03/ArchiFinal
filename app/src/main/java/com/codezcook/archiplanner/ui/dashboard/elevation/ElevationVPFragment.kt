package com.codezcook.archiplanner.ui.dashboard.elevation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.adapter.ElevationFragmentAdapter
import com.codezcook.archiplanner.adapter.FragmentAdapter
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_planvp.*

class ElevationVPFragment() : Fragment(R.layout.fragment_planvp) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ElevationFragmentAdapter(childFragmentManager,lifecycle,Constants.elevationList.size)
        vp_plans.adapter = adapter
        vp_plans.setCurrentItem(Constants.curElevationPos,false)
        Constants.curElevationResponseItem = Constants.elevationList[Constants.curElevationPos]

        setBannerAd()

       // Toast.makeText(requireContext(),"curplanpos ${Constants.curPlanPos}",Toast.LENGTH_SHORT).show()


            vp_plans.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Constants.curElevationResponseItem = Constants.elevationList[position]
                    Constants.curElevationPos = position
                    super.onPageSelected(position)
                }
            })

    }

    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_download.loadAd(adRequest)
        }
    }
}