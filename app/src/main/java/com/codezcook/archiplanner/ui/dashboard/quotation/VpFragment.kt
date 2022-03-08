package com.codezcook.archiplanner.ui.dashboard.quotation

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.media.audiofx.EnvironmentalReverb
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.adapter.VpAdapter
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.fragment_vp.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class VpFragment : Fragment(R.layout.fragment_vp) {

    lateinit var progressDialog : ProgressDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = listOf<Int>(0,1,2,3,4,5,6,7,8,9,10)
        val adapter = VpAdapter()
        vp_pdf.adapter = adapter
        adapter.topicList = list
        adapter.notifyDataSetChanged()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Preparing Pdf")
        progressDialog.show()

        Constants.requestPermissions(requireActivity())
        if (Constants.hasLocationPermissions(requireContext())) {
            vpTransition(adapter)
        }




    }
    fun vpTransition(adapter : VpAdapter){
        var currentPage = -1
        var timer: Timer? = null
        val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
        val PERIOD_MS: Long = 1000 // time in milliseconds between successive task executions
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == adapter.itemCount) {
                currentPage = 100
                timer?.cancel()
                progressDialog.dismiss()
                createPdf()
            }
            if (vp_pdf != null && currentPage != 100){
                vp_pdf.setCurrentItem(currentPage++, true)
            }
        }
        timer = Timer() // This will create a new Thread

        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)



    }
    fun goToFileIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        val contentUri = FileProvider.getUriForFile(context, "com.example.homefolder.example.provider.archplanner", file)
        val mimeType = context.contentResolver.getType(contentUri)
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        intent.setDataAndType(contentUri, mimeType)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return intent
    }
    fun makePdf(document: PdfDocument,folderName: String,fileName: String){

      /*  val direct = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/ArchPlanner/$folderName/")
        if (!direct.exists()) {
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
            activity?.onBackPressed()
            Toast.makeText(requireContext(),"Succeeded", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }catch (e:java.lang.Exception){
            progressDialog.dismiss()
            activity?.onBackPressed()
            Toast.makeText(requireContext(),"failed ${e.message}", Toast.LENGTH_SHORT).show()
        }
        document.close()
    }

    fun createPdf() {
        val document = PdfDocument()
        val viewlist = mutableListOf<View>()
        val views = Constants.viewlist

        for (pos in views.indices) {
            val pageInfo =
//            PdfDocument.PageInfo.Builder(views[pos].width, views[pos].height, pos + 1).create()
            PdfDocument.PageInfo.Builder(views[pos].width, views[pos].height, pos + 1).create()
            val startPage = document.startPage(pageInfo)
//            val canvas = startPage.canvas
            val bitmap = Bitmap.createBitmap(views[pos].measuredWidth, views[pos].measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            Bitmap.createScaledBitmap(bitmap, 210, 500, true)
//            view.draw(canvas)
            startPage.canvas.drawBitmap(bitmap, 0F, 0F, null)
            views[pos].draw(canvas)
            document.finishPage(startPage)
        }
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
        val file = dateFormat.format(date).toString() + ".pdf"
        makePdf(document, "Quotation", file)

        /*  val file = File(Environment.getExternalStorageDirectory(),"Quotation.pdf")
        try {
            document.writeTo(FileOutputStream(file))
            val intent = goToFileIntent(requireContext(), file)
            startActivity(Intent.createChooser(intent, "share by"))
            Toast.makeText(requireContext(),"Succeeded", Toast.LENGTH_SHORT).show()
        }catch (e:java.lang.Exception){
            Toast.makeText(requireContext(),"failed ${e.message}", Toast.LENGTH_SHORT).show()
        }
        document.close()
    } */

    }

}