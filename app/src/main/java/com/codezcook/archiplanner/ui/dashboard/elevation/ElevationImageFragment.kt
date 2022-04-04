package com.codezcook.archiplanner.ui.dashboard.elevation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.dialog.BottomDialog
import com.codezcook.archiplanner.ui.dashboard.fragments.ImageActivity
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_plan_view.fragmentview
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_elevationimage.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_grey.cd_grey
import kotlinx.android.synthetic.main.fragment_grey.iv_grey
import kotlinx.android.synthetic.main.fragment_grey.tv_bgcenter
import kotlinx.android.synthetic.main.fragment_grey.tv_bggrey
import kotlinx.android.synthetic.main.fragment_grey.tv_celldata
import kotlinx.android.synthetic.main.fragment_grey.tv_contactdata
import kotlinx.android.synthetic.main.fragment_grey.tv_namedata
import kotlinx.android.synthetic.main.fragment_grey.tv_placedata
import kotlinx.android.synthetic.main.fragment_object.*
import kotlinx.android.synthetic.main.fragment_planview.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class ElevationImageFragment(val value : Int) : Fragment(R.layout.fragment_elevationimage) {

    var isFABOpen = false
    private var mInterstitialAd: InterstitialAd? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),getString(R.string.elevationdownload_interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        fab_mainfloor.setOnClickListener {
            if (isFABOpen){
                closeFABMenu2()
            }else{
                showFABMenu2()
            }
        }


        if (Constants.downloadType == Constants.PAIDDOWNLOAD){
            iv_elevat_waterlogo.visibility = View.INVISIBLE
        }
        if (Constants.downloadType == Constants.FREEDOWNLOAD){
            iv_elevat_waterlogo.visibility = View.VISIBLE
        }

        closeFABMenu2()


        fab_downloadfloor.setOnClickListener {
            if (!Constants.isSubscribed) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                }
            }
            download()
        }

    }
    private fun closeFABMenu2() {
        isFABOpen = false
        tv_fab3floor.visibility = View.GONE
        fab_downloadfloor.animate().translationY(0F)
        tv_fab3floor.animate().translationY(0F)
        fab_mainfloor.setImageResource(R.drawable.ic_action_edit)
    }
    fun createViewBitmap(view: View): Bitmap? {
        //    Toast.makeText(requireContext(),"width ${view.width} and height ${view.height}",Toast.LENGTH_SHORT).show()
        val bitmap = Bitmap.createBitmap(view.width,view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawable = view.background
        if (drawable != null){
            drawable.draw(canvas)
        }else{
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }
    fun makePdf(view: View,folderName: String,fileName: String){
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(  view.width,  view.height,  1).create()
        val startPage = document.startPage(pageInfo)
        val canvas = startPage.canvas
        view.draw(canvas)
        document.finishPage(startPage)

      //  val direct = File(Environment.getExternalStorageDirectory().toString() + "/ArchPlanner/$folderName/")

      /*  if (!direct.exists()) {
            val wallpaperDirectory = File("/sdcard/ArchPlanner/$folderName/")
            wallpaperDirectory.mkdirs()
        } */
        val parent = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/ArchiPlanner/$folderName")
        if (!parent.exists()) {
            parent.mkdirs();
            // Toast.makeText(requireContext(), "not exist", Toast.LENGTH_SHORT).show();
        }

        val file = File(parent, fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            document.writeTo(FileOutputStream(file))
            out.flush()
            out.close()
            Toast.makeText(requireContext(),"Succeeded", Toast.LENGTH_SHORT).show()
        }catch (e:java.lang.Exception){
            Toast.makeText(requireContext(),"failed ${e.message}", Toast.LENGTH_SHORT).show()
        }
        document.close()
    }
    fun saveGallery(fileName:String,imageToSave:Bitmap,folderName : String){
      //  val direct = File(Environment.getExternalStorageDirectory().toString() + "/ArchPlanner/$folderName/")
        val parent = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/ArchiPlanner/$folderName")
        if (!parent.exists()) {
            parent.mkdirs();
            // Toast.makeText(requireContext(), "not exist", Toast.LENGTH_SHORT).show();
        }
      /*  if (!direct.exists()) {
            val wallpaperDirectory = File("/sdcard/ArchPlanner/$folderName/")
            wallpaperDirectory.mkdirs()
        } */

        val file = File(parent, fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            if (fileName == Constants.viewfilesname[5]){
                Toast.makeText(requireContext(),"Saved Successfully",Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(),"Something went wrong ${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
    fun showPayment(){
        val bottomSheet = BottomDialog(requireContext())
        bottomSheet.createBottomDialog(this)
    }
    fun download() {
        if (!Constants.isSubscribed) {
         //   showPayment()
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            }
        } else {
            Constants.requestPermissions(requireActivity())
            if (Constants.hasLocationPermissions(requireContext())) {
                val data = Constants.curElevationResponseItem
                val bitmap = createViewBitmap(ct_elevation)
                makePdf(ct_elevation,data.title,"elevation.pdf")
                if (bitmap != null) {
                    saveGallery("elevation.jpg", bitmap,data.title)
                }
            }
        }
    }
    private fun showFABMenu2() {
        isFABOpen = true
        tv_fab3floor.visibility = View.VISIBLE
        fab_downloadfloor.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        tv_fab3floor.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        fab_mainfloor.setImageResource(R.drawable.ic_action_close)
    }

    override fun onResume() {
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
        val data = Constants.curElevationResponseItem
        val url = "https://admin.archiplanner.in/storage/app/public/${data.folder}/${data.image}"
        Glide.with(requireContext()).load(url).into(iv_elevat)
        iv_elevat.setOnClickListener {
            Constants.url = url
            startActivity(Intent(requireContext(),ImageActivity::class.java))
        }


        super.onResume()
    }
    fun textcolor(isavailable : Boolean, tv : TextView, list : List<EditText>, cd : CardView){
        var defaultcolor = if (isavailable) Constants.textcolor else ContextCompat.getColor(requireContext(),R.color.black)
        val colorpicker = AmbilWarnaDialog(requireContext(),defaultcolor,object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                defaultcolor = color

                if (list.isEmpty()){
                    Constants.bordercolor1 = color
                }else if(list.size == 1 ){
                    Constants.textcolor2 = color
                }
                else{
                    Constants.textcolor1 = color
                }
                Constants.bordercolor = color
                for (tv in list){
                    tv.setTextColor(defaultcolor)
                }
                tv.setTextColor(defaultcolor)
                cd.setCardBackgroundColor(defaultcolor)
            }

        })
        colorpicker.show()
    }


    fun detailsEditDialog(isavailable: Boolean,isavailable2: Boolean,isavailable3: Boolean){
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val customview = layoutInflater.inflate(R.layout.dialog_contactedit,null,false)
        alertDialog.setView(customview)
        val cd1 = customview.findViewById<CardView>(R.id.cd_textcolor)
        val cd2 = customview.findViewById<CardView>(R.id.cd_bordercolor)
        val cd3 = customview.findViewById<CardView>(R.id.cd_textcolor2)
        val tv1 = customview.findViewById<TextView>(R.id.tv_textcolor)
        val tv2 = customview.findViewById<TextView>(R.id.tv_bordercolor)
        val tv3 = customview.findViewById<TextView>(R.id.tv_textcolor2)
        val ed1 = customview.findViewById<EditText>(R.id.ed_name)
        val ed2 = customview.findViewById<EditText>(R.id.ed_cell)
        val ed3 = customview.findViewById<EditText>(R.id.ed_place)
        val ed4 = customview.findViewById<EditText>(R.id.ed_service)
        val submit = customview.findViewById<Button>(R.id.bt_submit)
        ed1.setText(Constants.customerDetails.name)
        ed2.setText(Constants.customerDetails.phone_number)
        ed3.setText(Constants.customerDetails.location)
        ed4.setText(Constants.customerDetails.services)
        if (isavailable){
            cd1.setCardBackgroundColor(Constants.textcolor)
            tv1.setTextColor(Constants.textcolor)
            ed1.setTextColor(Constants.textcolor)
            ed2.setTextColor(Constants.textcolor)
            ed3.setTextColor(Constants.textcolor)
        }
        if (isavailable3){
            cd3.setCardBackgroundColor(Constants.textcolor2)
            tv3.setTextColor(Constants.textcolor2)
            ed4.setTextColor(Constants.textcolor2)
        }
        if (isavailable2){
            cd2.setCardBackgroundColor(Constants.bordercolor)
            tv2.setTextColor(Constants.bordercolor)
        }
        cd1.setOnClickListener {
            textcolor(isavailable,tv1, listOf(ed1,ed2,ed3),cd1)
        }
        cd2.setOnClickListener {
            textcolor(isavailable2,tv2, listOf(),cd2)
        }
        cd3.setOnClickListener {
            textcolor(isavailable,tv3, listOf(ed4),cd3)
        }
        submit.setOnClickListener {
            Constants.customerDetails.name = ed1.text.toString()
            Constants.customerDetails.services = ed4.text.toString()
            Constants.customerDetails.phone_number = ed2.text.toString()
            Constants.customerDetails.location = ed3.text.toString()
            Constants.textcolor = Constants.textcolor1
            Constants.bordercolor = Constants.bordercolor1
         //   Constants.isDownload = true
          //  startActivity(Intent(requireContext(), HomeActivity::class.java))
            onResume()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}