package com.codezcook.archiplanner.ui.dashboard.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_plan.*
import java.util.*

class PlanFragment : Fragment(R.layout.fragment_plan) {

    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var viewmodel : PlanViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)


        viewmodel.updateUserResponseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(requireContext(),"$it", Toast.LENGTH_LONG).show()
        })

        viewmodel.errorUpdateUserResponseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })


        setBannerAd()



        cd_north.setOnClickListener {
        //viewmodel.updatePaymentDetails(firebaseAuth.currentUser?.uid!!,1,"1","newplan","${Calendar.getInstance().time}","${Calendar.getInstance().time}","${Calendar.getInstance().time.time}","${Calendar.getInstance().time.time}")
            Constants.curPlan = Constants.NORTHPLAN
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_planFragment_to_planListFragment)
        }
        cd_south.setOnClickListener {
            Constants.curPlan = Constants.SOUTHPLAN
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_planFragment_to_planListFragment)
        }
        cd_east.setOnClickListener {
            Constants.curPlan = Constants.EASTPLAN
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_planFragment_to_planListFragment)
        }
        cd_west.setOnClickListener {
            Constants.curPlan = Constants.WESTPLAN
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_planFragment_to_planListFragment)
        }


    }

    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_plan.loadAd(adRequest)
        } else {
            banner_plan.visibility = View.GONE
        }
    }

}