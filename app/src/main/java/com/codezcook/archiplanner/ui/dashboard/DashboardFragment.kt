package com.codezcook.archiplanner.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.dialog.BottomDialog
import com.codezcook.archiplanner.payment.CheckoutActivity
import com.codezcook.archiplanner.sharedpref.SharedPref
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    lateinit var viewmodel : PlanViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        val sharedPref = SharedPref(requireContext())

        viewmodel.getMyProfileData(sharedPref.getUserId().toString())

        setBannerAd()



        viewmodel.myProfileLive.observe(viewLifecycleOwner, Observer {
            Constants.userData  = it
            if (it.plan != "Free"){
                sharedPref.saveSubscription("A week ago")
            }
            Constants.isSubscribed =  sharedPref.isSubscribed()
            if (!Constants.isSubscribed) {
                if (!Constants.isDownload){
                    showPayment()
                }
            }
            val countForUser = it.curCount
            val countForFixed = it.TotalCount

            Constants.curCount = countForUser
            Constants.curFixedCount = countForFixed

            //   Toast.makeText(this, "curCount ${countForUser} and fixedCount $countForFixed", Toast.LENGTH_SHORT).show()

            if (Constants.curCount >= Constants.curFixedCount) {
                Constants.downloadType = Constants.FREEDOWNLOAD
            } else {
                Constants.downloadType =  Constants.PAIDDOWNLOAD
            }


        })

        viewmodel.errorMyProfileLive.observe(viewLifecycleOwner, Observer {

        })


        cd_plan.setOnClickListener {
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_nav_home_to_planFragment)
        }

        cd_elevation.setOnClickListener {
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_nav_home_to_elevationFragment)
        }

        cd_qa.setOnClickListener {
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_nav_home_to_topicFragment)
        }
        cd_quotation.setOnClickListener {
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_nav_home_to_getDetailsFragment)
        }
    }

    private fun setBannerAd(){

        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_dashboard.loadAd(adRequest)
        } else {
            banner_dashboard.visibility = View.GONE
        }

    }

    fun showPayment(){
        val bottomSheet = BottomDialog(requireContext())
        bottomSheet.createBottomDialog(this)
    }


}