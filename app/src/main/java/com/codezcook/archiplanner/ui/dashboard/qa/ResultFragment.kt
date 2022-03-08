package com.codezcook.archiplanner.ui.dashboard.qa

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_qatest.*
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment(R.layout.fragment_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as HomeActivity
        val favt = activity.findViewById<ImageButton>(R.id.ib_allfavts)
        favt.visibility = View.INVISIBLE

        setBannerAd()

        tv_totalmarksdata.text = Constants.totalmarks
        tv_percentdata.text = Constants.totalpercentage
        tv_timetakendata.text = Constants.timetaken

        val answers = Constants.totalmarks.toInt()
        val percent = Constants.totalpercent
        if (percent>80){
            val veryGood = "Very Good"
            tv_finalresult.setTextColor(Color.parseColor("#2E7D32"))
            tv_finalresult.text = veryGood
        }else if (percent>=50){
            val good = "Good"
            tv_finalresult.setTextColor(Color.parseColor("#f47100"))
            tv_finalresult.text = good
        }else{
            val poor = "Poor"
            tv_finalresult.setTextColor(Color.parseColor("#C62828"))
            tv_finalresult.text = poor
        }
    }
    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_result.loadAd(adRequest)
        } else {
            banner_result.visibility = View.GONE
        }
    }
}