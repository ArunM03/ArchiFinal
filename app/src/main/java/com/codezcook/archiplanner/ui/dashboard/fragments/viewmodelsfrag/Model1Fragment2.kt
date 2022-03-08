package com.codezcook.archiplanner.ui.dashboard.fragments.viewmodelsfrag

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.ui.dashboard.fragments.ImageActivity
import com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments.getPos
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_plan_view.fragmentview
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_model.*
import kotlinx.android.synthetic.main.fragment_planview.*
import kotlinx.android.synthetic.main.rv_plans.view.*

@Suppress("DEPRECATION")
class Model1Fragment2 : Fragment(R.layout.fragment_model) {

    var imageReady = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  Toast.makeText(requireContext(),"Executing Model1 ", Toast.LENGTH_LONG).show()

/*
        if (Constants.isSubscribed){
            if (!Constants.isFreeUser) {
                iv_waterlogo_model.visibility = View.INVISIBLE
            } else {
                iv_waterlogo_model.visibility = View.VISIBLE
            }
        }else{
            iv_waterlogo_model.visibility = View.VISIBLE
        }*/

        if (Constants.downloadType == Constants.PAIDDOWNLOAD){
            iv_waterlogo_model.visibility = View.INVISIBLE
        }
        if (Constants.downloadType == Constants.FREEDOWNLOAD){
            iv_waterlogo_model.visibility = View.VISIBLE
        }

        val name = "NAME:${Constants.contactDetails.name}"
        val cell = "CELL:${Constants.contactDetails.phone_number}"
        val place = "PLACE:${Constants.contactDetails.location}"
        val service = Constants.contactDetails.services
/*        tv_namedatacolor.text = name
        tv_celldatacolor.text = cell
        tv_placedatacolor.text = place
        tv_contactdatacolor.text = service*/



        if (Constants.bordercolor != 0){
            /*          cd_color.strokeColor = Constants.bordercolor
                      tv_bgcentercolor.setBackgroundColor(Constants.bordercolor)
                      tv_bgcolor.setBackgroundColor(Constants.bordercolor)*/
        }
        if (Constants.textcolor != 0){
            /*         tv_celldatacolor.setTextColor(Constants.textcolor)
                     tv_namedatacolor.setTextColor(Constants.textcolor)
                     tv_placedatacolor.setTextColor(Constants.textcolor)*/
        }
        if (Constants.textcolor2 != 0){
            /*         tv_contactdatacolor.setTextColor(Constants.textcolor2)*/
        }

        val data = Constants.curPlanResponseItem
        val imageslist = data.images?.split(",")?.toTypedArray()
        val pos = imageslist?.let { getPos(it,"m1") }
        var url = ""
        pos?.let {
            val image = imageslist[pos]
            url = "https://admin.archiplanner.in/storage/app/public/${image}"
            Glide.with(requireContext()).load(url).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar_model.visibility = View.INVISIBLE
                    if (Constants.isDownload){
               /*         if (Constants.downloadFrom == Constants.MODELS){
                            Constants.views.add(ct_model)
                            fragmentviewmodel.findNavController().navigate(R.id.action_model1Fragment_to_greyFragment)
                            //     fragmentviewmodel.findNavController().navigate(R.id.action_model1Fragment_to_greyFragment)
                        }*/
                        if (Constants.downloadFrom == Constants.PLANS){
                            Constants.views.add(ct_model)
                            fragmentContainerView2.findNavController().navigate(R.id.action_model1Fragment2_to_colorFragment)
                            //  fragmentContainerView2.findNavController().navigate(R.id.action_model1Fragment2_to_colorFragment)
                        }
                    }
                    imageReady = true
                    return false
                }

            }) .into(iv_model)
          //  Toast.makeText(requireContext(),imageslist[pos],Toast.LENGTH_LONG).show()
        }
        if (pos == null){
            iv_model.visibility = View.GONE
          //  iv_waterlogo_model.visibility = View.GONE
            tv_model_comingsoon.visibility = View.VISIBLE
        }else{
            iv_model.visibility = View.VISIBLE
          //  iv_waterlogo_model.visibility = View.VISIBLE
            tv_model_comingsoon.visibility = View.GONE
        }

        iv_model.setOnClickListener {
            Constants.url = url
            Constants.isModel23 = false
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
    /*    while (!imageReady){
            if (imageReady){*/
     //   Handler().postDelayed({

     /*       }
        }*/
    //    },1000)

    }
   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = "NAME:${Constants.contactDetails.name}"
        val cell = "CELL:${Constants.contactDetails.phone_number}"
        val place = "PLACE:${Constants.contactDetails.location}"
        val service = Constants.contactDetails.services
        tv_namedatacolor.text = name
        tv_celldatacolor.text = cell
        tv_placedatacolor.text = place
        tv_contactdatacolor.text = service

        Toast.makeText(requireContext(),"Executing Model1 ",Toast.LENGTH_LONG).show()

        if (Constants.bordercolor != 0){
            cd_color.strokeColor = Constants.bordercolor
            tv_bgcentercolor.setBackgroundColor(Constants.bordercolor)
            tv_bgcolor.setBackgroundColor(Constants.bordercolor)
        }
        if (Constants.textcolor != 0){
            tv_celldatacolor.setTextColor(Constants.textcolor)
            tv_namedatacolor.setTextColor(Constants.textcolor)
            tv_placedatacolor.setTextColor(Constants.textcolor)
        }
        if (Constants.textcolor2 != 0){
            tv_contactdatacolor.setTextColor(Constants.textcolor2)
        }

        val data = Constants.curPlanResponseItem
        val imageslist = data.images?.split(",")?.toTypedArray()
        val pos = imageslist?.let { getPos(it,"m1") }
        var url = ""
        pos?.let {
            val image = imageslist[pos]
            url = "https://admin.archiplanner.in/storage/app/public/${image}"
            Glide.with(requireContext()).load(url).into(iv_color)
        }
        if (pos == null){
            cd_color.visibility = View.GONE
            tv_color_comingsoon.visibility = View.VISIBLE
        }else{
            cd_color.visibility = View.VISIBLE
            tv_color_comingsoon.visibility = View.GONE
        }

//        val data = Constants.curPlanResponseItem
//        val imageslist = data.images?.split(",")?.toTypedArray()
//        val url = "https://admin.archiplanner.in/storage/app/public/${imageslist?.get(3)}"
//        Glide.with(requireContext()).load(url).into(iv_color)
        iv_color.setOnClickListener {
            Constants.url = url
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
        Handler().postDelayed({
            if (Constants.isDownload){
                if (Constants.downloadFrom == Constants.MODELS){
                    Constants.views.add(ct_color)
                    fragmentviewmodel.findNavController().navigate(R.id.action_model1Fragment_to_greyFragment)
                }
                if (Constants.downloadFrom == Constants.PLANS){
                    Constants.views.add(ct_color)
                    fragmentContainerView2.findNavController().navigate(R.id.action_model1Fragment2_to_colorFragment)
                }
            }
        },500)

    }*/
}