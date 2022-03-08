package com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.sharedpref.SharedPref
import com.codezcook.archiplanner.ui.dashboard.fragments.ImageActivity
import com.codezcook.archiplanner2.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_object.*
import kotlinx.android.synthetic.main.fragment_planview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@Suppress("DEPRECATION")
class ObjectFragment() : Fragment(R.layout.fragment_object) {

    var imageReady = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*
        if (Constants.isSubscribed){
            if (!Constants.isFreeUser) {
                iv_waterlogo_object.visibility = View.INVISIBLE
            } else {
                iv_waterlogo_object.visibility = View.VISIBLE
            }
        }else{
            iv_waterlogo_object.visibility = View.VISIBLE
        }
*/




        if (Constants.downloadType == Constants.PAIDDOWNLOAD){
            iv_waterlogo_object.visibility = View.INVISIBLE
        }
        if (Constants.downloadType == Constants.FREEDOWNLOAD){
            iv_waterlogo_object.visibility = View.VISIBLE
        }



        val name = "NAME:${Constants.contactDetails.name}"
        val cell = "CELL:${Constants.contactDetails.phone_number}"
        val place = "PLACE:${Constants.contactDetails.location}"
        val service = Constants.contactDetails.services
        tv_namedataobject.text = name
        tv_celldataobject.text = cell
        tv_placedataobject.text = place
        tv_contactdataobject.text = service

        if (Constants.bordercolor != 0){
            cd_object.strokeColor = Constants.bordercolor
            tv_bgcenterobject.setBackgroundColor(Constants.bordercolor)
            tv_bgobject.setBackgroundColor(Constants.bordercolor)
        }
        if (Constants.textcolor != 0){
            tv_celldataobject.setTextColor(Constants.textcolor)
            tv_namedataobject.setTextColor(Constants.textcolor)
            tv_placedataobject.setTextColor(Constants.textcolor)
            tv_contactdataobject.setTextColor(Constants.textcolor)
        }
        if (Constants.textcolor2 != 0){
            tv_contactdataobject.setTextColor(Constants.textcolor2)
        }


        val data = Constants.curPlanResponseItem
        val imageslist = data.images?.split(",")?.toTypedArray()
        val pos = imageslist?.let { getPos(it,"f") }
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

                        progressbar_object.visibility = View.INVISIBLE
                        setDownloadFunctions()
                        imageReady = true
                        return false
                    }

                }) .into(iv_object)

        //    Toast.makeText(requireContext(),imageslist[pos],Toast.LENGTH_LONG).show()
        }
        if (pos == null){
            cd_object.visibility = View.GONE
            tv_object_comingsoon.visibility = View.VISIBLE
        }else{
            cd_object.visibility = View.VISIBLE
            tv_object_comingsoon.visibility = View.GONE
        }

        iv_object.setOnClickListener {
            Constants.url = url
            Constants.isModel23 = false
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
/*        while (!imageReady) {
            if (imageReady) {
                //Handler().postDelayed({

            }
        }*/
    //    },1000)


    }

    fun setDownloadFunctions() {
        if (Constants.isDownload) {
            if (Constants.downloadFrom == Constants.PLANS) {
                Constants.isDownload = false
                Constants.views.add(ct_object)
             //   SharedPref(requireContext()).addCount(Constants.userCount++)
                lifecycleScope.launch {
                    delay(500)
                    download()
                }
            }
/*            if (Constants.downloadFrom == Constants.MODELS) {
                Constants.isDownload = false
                // if (Constants.downloadType == Constants.PAIDDOWNLOAD){
                Constants.views.add(ct_object)
                //   }
                fragmentviewmodel.findNavController()
                    .navigate(R.id.action_objectFragment_to_model3Fragment)
            }*/
        }    }



    fun savetogallery(bitmap: Bitmap, bitmapname  : String) : String{
        return  MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "Archi Planner", bitmapname)
    }

    fun saveGallery(fileName:String,imageToSave:Bitmap,folderName : String){
    //    val direct = File(Environment.getExternalStorageDirectory().toString() + "/ArchPlanner/$folderName/")

        val parent = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/Archi Planner/$folderName")
        if (!parent.exists()) {
            parent.mkdirs();
            // Toast.makeText(requireContext(), "not exist", Toast.LENGTH_SHORT).show();
        }
     /*   if (!direct.exists()) {
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
            if (Constants.downloadType == Constants.PAIDDOWNLOAD) {
                if (fileName == Constants.viewfilesname[5]){
                    Toast.makeText(requireContext(),"Saved Successfully on the location ${file.path}",Toast.LENGTH_SHORT).show()
                }
            }
            if (Constants.downloadType == Constants.FREEDOWNLOAD) {
                if (fileName == Constants.viewfilesname[3]){
                    Toast.makeText(requireContext(),"Saved Successfully on the location ${file.path}",Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(),"Something went wrong ${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

    fun saveGallery2(fileName:String,imageToSave:Bitmap,folderName : String){
        //    val direct = File(Environment.getExternalStorageDirectory().toString() + "/ArchPlanner/$folderName/")

        val parent = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/Archi Planner/$folderName")
        if (!parent.exists()) {
            parent.mkdirs();
            // Toast.makeText(requireContext(), "not exist", Toast.LENGTH_SHORT).show();
        }
        /*   if (!direct.exists()) {
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
            if (fileName == Constants.viewfilesname2[5]){
                Toast.makeText(requireContext(),"Saved Successfully on the location ${file.path}",Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(),"Something went wrong ${e.message}",Toast.LENGTH_SHORT).show()
        }
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

        val parent = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/Archi Planner/$folderName")
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
            document.writeTo(FileOutputStream(file))
            out.flush()
            out.close()
        //   Toast.makeText(requireContext(),"Succeeded", Toast.LENGTH_SHORT).show()
        }catch (e:java.lang.Exception){
            Toast.makeText(requireContext(),"failed ${e.message}", Toast.LENGTH_SHORT).show()
        }
        document.close()
    }
    fun useDownloadManager(videoquality : String){
        val downloadManager = DownloadManager.Request(videoquality.toUri())
        downloadManager.setTitle("dwgfile" + ".dwg")
        downloadManager.setDescription("File downloaded")
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
       // downloadManager.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"asdlk" + ".dwg")
       // val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/ArchPlanner/${Constants.curPlanResponseItem.name}/"
       // downloadManager.setDestinationInExternalPublicDir(path,"dwgfile.dwg")
        downloadManager.setDestinationInExternalPublicDir("Download/Archi Planner/${Constants.curPlanResponseItem.name}/","dwgfile.dwg")
        // downloadManager.setDestinationUri(Uri.parse("file://FreakyVideos" + "/myfile.mp4" ))
        val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(downloadManager)
    }

    fun download() {
        Constants.requestPermissions(requireActivity())
        if (Constants.hasLocationPermissions(requireContext())) {
            val data = Constants.curPlanResponseItem
            val imageslist = data.images?.split(",")?.toTypedArray()
            val url = "https://admin.archiplanner.in/storage/app/public/${imageslist?.get(imageslist.size-1)}"

        //    Toast.makeText(requireContext(),"size ${Constants.views.size}",Toast.LENGTH_SHORT).show()
            try {
                if (Constants.downloadType == Constants.PAIDDOWNLOAD){
                    for (views in Constants.views) {
                        //  Toast.makeText(requireContext(),"index ${Constants.views.indexOf(views)} width ${views.width} and height ${views.height}",Toast.LENGTH_SHORT).show()
                        val bitmap = createViewBitmap(views)
                        makePdf(
                            views,
                            Constants.curPlanResponseItem.name,
                            Constants.viewfilespdf[Constants.views.indexOf(views)]
                        )
                        if (bitmap != null) {
                            saveGallery(
                                Constants.viewfilesname[Constants.views.indexOf(views)],
                                bitmap,
                                Constants.curPlanResponseItem.name
                            )
                        }
                    }
                }else{
                    for (views in Constants.views) {
                        //  Toast.makeText(requireContext(),"index ${Constants.views.indexOf(views)} width ${views.width} and height ${views.height}",Toast.LENGTH_SHORT).show()
                        val bitmap = createViewBitmap(views)
                        makePdf(
                            views,
                            Constants.curPlanResponseItem.name,
                            Constants.viewfilespdf2[Constants.views.indexOf(views)]
                        )
                        if (bitmap != null) {
                            saveGallery2(
                                Constants.viewfilesname2[Constants.views.indexOf(views)],
                                bitmap,
                                Constants.curPlanResponseItem.name
                            )
                        }
                    }
                }
            }catch (e:Exception){
                Toast.makeText(requireContext(),"view size is ${Constants.views.size} ${e.message}",Toast.LENGTH_SHORT).show()

            }

            try {
                if (Constants.downloadType == Constants.PAIDDOWNLOAD){
                    useDownloadManager(url)
                }
            }catch (e : Exception){
                Toast.makeText(requireContext(),"${e.message}",Toast.LENGTH_SHORT).show()
            }

            //val issaved = bitmap?.let { it1 -> savetogallery(it1, "Pic_$randomString") }
          /*  if (issaved != null) {
                Toast.makeText(
                    requireContext(),
                    "Quote successfully saved!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), "Failed to save!", Toast.LENGTH_SHORT)
                    .show()
            } */
        }
    }
}