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
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.ui.dashboard.fragments.ImageActivity
import com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments.getPos
import com.codezcook.archiplanner2.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_plan_view.fragmentview
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_model.*
import kotlinx.android.synthetic.main.fragment_planview.*

@Suppress("DEPRECATION")
class Model2Fragment2 : Fragment(R.layout.fragment_model) {

    var imageReady = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Toast.makeText(requireContext(),"Executing Model2 ",Toast.LENGTH_LONG).show()

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
/*        tv_namedata.text = name
        tv_celldata.text = cell
        tv_placedata.text = place
        tv_contactdata.text = service*/
/*

        if (Constants.bordercolor != 0){
            cd_grey.strokeColor = Constants.bordercolor
            tv_bgcenter.setBackgroundColor(Constants.bordercolor)
            tv_bggrey.setBackgroundColor(Constants.bordercolor)
        }
        if (Constants.textcolor != 0){
            tv_celldata.setTextColor(Constants.textcolor)
            tv_namedata.setTextColor(Constants.textcolor)
            tv_placedata.setTextColor(Constants.textcolor)
            tv_contactdata.setTextColor(Constants.textcolor)
        }
        if (Constants.textcolor2 != 0){
            tv_contactdata.setTextColor(Constants.textcolor2)
        }
*/


        //  Toast.makeText(requireContext(),"model1", Toast.LENGTH_LONG).show()

        val data = Constants.curPlanResponseItem
        val imageslist = data.images?.split(",")?.toTypedArray()
        val pos = imageslist?.let { getPos(it,"m2") }
        var url = ""
        pos?.let {
            val image = imageslist[pos]
            url = "https://admin.archiplanner.in/storage/app/public/${image}"
            //   Glide.with(requireContext()).load(url).into(iv_model)
            if (Constants.downloadType == Constants.PAIDDOWNLOAD){
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
                        imageReady = true
                        setDownload()
                        return false
                    }

                }) .into(iv_model)
            }
            if (Constants.downloadType == Constants.FREEDOWNLOAD){
                Glide.with(requireContext()).load(url).listener(object : RequestListener<Drawable>{
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
                        imageReady = true
                        return false
                    }

                })
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(70)))
                    .into(iv_model)
            }
            if (!Constants.isSubscribed) {
            }

         //   Toast.makeText(requireContext(),imageslist[pos],Toast.LENGTH_LONG).show()
        }

        if (Constants.isDownload){
            if (Constants.downloadType == Constants.FREEDOWNLOAD){
                    fragmentContainerView2.findNavController().navigate(R.id.action_model2Fragment2_to_model3Fragment2)
            }
        }

        iv_model.setOnClickListener {
            Constants.url = url
            Constants.isModel23 = true
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
/*        while (!imageReady){
            if (imageReady){
       // Handler().postDelayed({

            }
            }*/
     //   },1000)

    }
    fun setDownload(){
        if (Constants.isDownload){
   /*         if (Constants.downloadFrom == Constants.MODELS){

                if (Constants.downloadType == Constants.PAIDDOWNLOAD){
                    Constants.views.add(ct_model)
                }

                fragmentviewmodel.findNavController().navigate(R.id.action_model2Fragment_to_colorFragment)
            }*/
            if (Constants.downloadFrom == Constants.PLANS){

                if (Constants.downloadType == Constants.PAIDDOWNLOAD){
                    Constants.views.add(ct_model)
                }

                fragmentContainerView2.findNavController().navigate(R.id.action_model2Fragment2_to_model3Fragment2)
                //    fragment2.findNavController().navigate(R.id.action_model2Fragment3_to_model3Fragment3)
            }
        }
    }
   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(),"Executing Model2 ",Toast.LENGTH_LONG).show()


        if (Constants.isSubscribed){
            iv_waterlogo_grey.visibility = View.INVISIBLE
        }else{
            iv_waterlogo_grey.visibility = View.VISIBLE
        }



        val name = "NAME:${Constants.contactDetails.name}"
        val cell = "CELL:${Constants.contactDetails.phone_number}"
        val place = "PLACE:${Constants.contactDetails.location}"
        val service = Constants.contactDetails.services
        tv_namedata.text = name
        tv_celldata.text = cell
        tv_placedata.text = place
        tv_contactdata.text = service

      //  Toast.makeText(requireContext(),"model2",Toast.LENGTH_LONG).show()

        if (Constants.bordercolor != 0){
            cd_grey.strokeColor = Constants.bordercolor
            tv_bgcenter.setBackgroundColor(Constants.bordercolor)
            tv_bggrey.setBackgroundColor(Constants.bordercolor)
        }
        if (Constants.textcolor != 0){
            tv_celldata.setTextColor(Constants.textcolor)
            tv_namedata.setTextColor(Constants.textcolor)
            tv_placedata.setTextColor(Constants.textcolor)
            tv_contactdata.setTextColor(Constants.textcolor)
        }
        if (Constants.textcolor2 != 0){
            tv_contactdata.setTextColor(Constants.textcolor2)
        }


        val data = Constants.curPlanResponseItem
        val imageslist = data.images?.split(",")?.toTypedArray()
        val pos = imageslist?.let { getPos(it,"m2") }
        var url = ""
        pos?.let {
            val image = imageslist[pos]
            url = "https://admin.archiplanner.in/storage/app/public/${image}"
            Glide.with(requireContext()).load(url).into(iv_grey)
        }
        if (pos == null){
            cd_grey.visibility = View.GONE
            tv_grey_comingsoon.visibility = View.VISIBLE
        }else{
            cd_grey.visibility = View.VISIBLE
            tv_grey_comingsoon.visibility = View.GONE
        }

        if (!Constants.isSubscribed){
            cd_grey.visibility = View.GONE
            val text = "Please Subscribe Yearly or Monthly pack to see this model"
            tv_grey_comingsoon.text = text
            tv_grey_comingsoon.visibility = View.VISIBLE
        }else{
            cd_grey.visibility = View.VISIBLE
            tv_grey_comingsoon.visibility = View.GONE
        }


//        val data = Constants.curPlanResponseItem
//        val imageslist = data.images?.split(",")?.toTypedArray()
//        val url = "https://admin.archiplanner.in/storage/app/public/${imageslist?.get(4)}"
//        Glide.with(requireContext()).load(url).into(iv_grey)
        iv_grey.setOnClickListener {
            Constants.url = url
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
        Handler().postDelayed({
            if (Constants.isDownload){
                if (Constants.downloadFrom == Constants.MODELS){
                    Constants.views.add(ct_grey)
                    fragmentviewmodel.findNavController().navigate(R.id.action_model2Fragment_to_colorFragment)
                }
                if (Constants.downloadFrom == Constants.PLANS){
                    Constants.views.add(ct_grey)
                    fragmentContainerView2.findNavController().navigate(R.id.action_model2Fragment2_to_model3Fragment2)
                }

            }
        },500)

    }*/
}