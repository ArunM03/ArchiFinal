package com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.ui.dashboard.fragments.ImageActivity
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_plan_view.fragmentview
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_planview.*
import kotlinx.android.synthetic.main.rv_plans.view.*

@Suppress("DEPRECATION")
class ColorFragment() : Fragment(R.layout.fragment_color) {

    var imageReady = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        if (Constants.isSubscribed){
            if (!Constants.isFreeUser) {
                iv_waterlogo_color.visibility = View.INVISIBLE
            } else {
                iv_waterlogo_color.visibility = View.VISIBLE
            }
        }else{
            iv_waterlogo_color.visibility = View.VISIBLE
        }

        */

        if (Constants.downloadType == Constants.PAIDDOWNLOAD){
            iv_waterlogo_color.visibility = View.INVISIBLE
        }
        if (Constants.downloadType == Constants.FREEDOWNLOAD){
            iv_waterlogo_color.visibility = View.VISIBLE
        }


        val name = "NAME:${Constants.contactDetails.name}"
        val cell = "CELL:${Constants.contactDetails.phone_number}"
        val place = "PLACE:${Constants.contactDetails.location}"
        val service = Constants.contactDetails.services
        tv_namedatacolor.text = name
        tv_celldatacolor.text = cell
        tv_placedatacolor.text = place
        tv_contactdatacolor.text = service

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
        val pos = imageslist?.let { getPos(it,"c") }
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
                    setDownloadFunctions()
                    imageReady = true
                    return false
                }

            }) .into(iv_color)
         //   Toast.makeText(requireContext(),imageslist[pos], Toast.LENGTH_LONG).show()
        }
        if (pos == null){
            cd_color.visibility = View.GONE
            tv_color_comingsoon.visibility = View.VISIBLE
        }else{
            cd_color.visibility = View.VISIBLE
            tv_color_comingsoon.visibility = View.GONE
        }
        //    val image = imageslist[getPos(imageslist,"b")]


        iv_color.setOnClickListener {
            Constants.url = url
            Constants.isModel23 = false
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
/*        while (!imageReady) {
            if (imageReady) {*/
                // Handler().postDelayed({

                //    },1000)
   /*         }
        }*/

    }
    fun setDownloadFunctions(){
        if (Constants.isDownload) {
    /*        if (Constants.downloadFrom == Constants.MODELS) {
                Constants.views.add(ct_color)
                fragmentviewmodel.findNavController()
                    .navigate(R.id.action_colorFragment_to_objectFragment2)
            }*/
            if (Constants.downloadFrom == Constants.PLANS) {
                Constants.views.add(ct_color)
                fragmentContainerView2.findNavController()
                    .navigate(R.id.action_colorFragment_to_model2Fragment2)
            }
        }
    }

}

 fun Fragment.getPos(list: Array<String>, type: String) : Int?{
    var newPos :Int? = null
    for (data in list){
        val datalist = mutableListOf<String>()
       if (data.contains(type)){
            datalist.add(data)
        }
       /* val datalist = data.filter {
            it in type
        }*/
        if (datalist.isNotEmpty()){
            newPos = list.indexOf(data)
        }
    }
    return newPos
}