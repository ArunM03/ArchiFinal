package com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments

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
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_plan_view.fragmentview
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_planview.*

@Suppress("DEPRECATION")
class GreyFragment() : Fragment(R.layout.fragment_grey) {

    var imageReady = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        if (Constants.isSubscribed){
            if (!Constants.isFreeUser) {
                iv_waterlogo_grey.visibility = View.INVISIBLE
            } else {
                iv_waterlogo_grey.visibility = View.VISIBLE
            }
        }else{
            iv_waterlogo_grey.visibility = View.VISIBLE
        }*/

        if (Constants.isDownload){

        }

        if (Constants.downloadType == Constants.PAIDDOWNLOAD){
            iv_waterlogo_grey.visibility = View.INVISIBLE
        }
        if (Constants.downloadType == Constants.FREEDOWNLOAD){
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


    }

    override fun onResume() {

        val data = Constants.curPlanResponseItem
        val imageslist = data.images?.split(",")?.toTypedArray()
        val pos = imageslist?.let { getPos(it,"b") }
        var url = ""
        pos?.let {
            val image = imageslist[pos]
            url = "https://admin.archiplanner.in/storage/app/public/${image}"
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
                    setDownloadFunctions()
                    //imageReady = true
                    return false
                }

            }) .into(iv_grey)
         //   Toast.makeText(requireContext(),imageslist[pos],Toast.LENGTH_LONG).show()
        }
        if (pos == null){
            cd_grey.visibility = View.GONE
            tv_grey_comingsoon.visibility = View.VISIBLE
        }else{
            cd_grey.visibility = View.VISIBLE
            tv_grey_comingsoon.visibility = View.GONE
        }


        iv_grey.setOnClickListener {
            Constants.url = url
            Constants.isModel23 = false
            startActivity(Intent(requireContext(),ImageActivity::class.java))
        }
      //  while (!imageReady){
           // if (imageReady){
            //    Handler().postDelayed({
      //  setDownloadFunctions()
              //  },1000)
   /*         }
        }*/

  //      Toast.makeText(requireContext(),"created grey ${Constants.curPlanResponseItem.name}",Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    private fun setDownloadFunctions() {
        if (Constants.isDownload) {
 /*           if (Constants.downloadFrom == Constants.MODELS) {
                Constants.views.add(ct_grey)
                fragmentviewmodel.findNavController()
                    .navigate(R.id.action_greyFragment_to_model2Fragment)
            }*/
            if (Constants.downloadFrom == Constants.PLANS) {
                Constants.views = mutableListOf()
                Constants.views.add(ct_grey)
                fragmentContainerView2.findNavController()
                    .navigate(R.id.action_greyFragment_to_model1Fragment2)
            }
        }
    }
}