package com.codezcook.archiplanner.ui.dashboard.elevation

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.adapter.ElevationAdapter
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.data.ElevationResponseItem
import com.codezcook.archiplanner.data.PlanResponseItem
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_elevation.*
import kotlinx.android.synthetic.main.fragment_elevationlist.*
import kotlinx.android.synthetic.main.fragment_object.*
import kotlinx.android.synthetic.main.fragment_planlist.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ElevationListFragment : Fragment(R.layout.fragment_elevationlist) {

    val elevationAdapter = ElevationAdapter()
    lateinit var viewmodel : PlanViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)


        viewmodel.getElevations()

        setBannerAd()



        rv_elevation.adapter = elevationAdapter
        rv_elevation.layoutManager = LinearLayoutManager(requireContext())

        elevationAdapter.setOnItemClickListener {
            Constants.curElevationResponseItem = it
            val planlist = elevationAdapter.elevationList
            Constants.curElevationPos = planlist.indexOf(it)
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_elevationListFragment_to_elevationVPFragment)
        }

        elevationAdapter.share {
            share(it.view, it.elevationResponseItem)
        }
        elevationAdapter.unSave {
            viewmodel.unSaveFavtElevation("0",it.id)
            elevationAdapter.notifyDataSetChanged()
        }

        elevationAdapter.save {
            viewmodel.saveFavtElevation("0",it.id)
            elevationAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(),"Saved Successfully",Toast.LENGTH_SHORT).show()
        }

        viewmodel.elevationLive.observe(viewLifecycleOwner, Observer {
            val curList = it.filter { item -> item.floor == Constants.curElevation }
            if (curList.isEmpty()){
                tv_comingsoon2.visibility = View.VISIBLE
            }else{
                tv_comingsoon2.visibility = View.GONE
            }
            elevationAdapter.elevationList = curList
            Constants.elevationList = curList
            elevationAdapter.notifyDataSetChanged()
        })

        viewmodel.errorElevationLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })
    }

    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_elevationlist.loadAd(adRequest)
        }else {
            banner_elevationlist.visibility = View.GONE
        }
    }

    override fun onResume() {
        elevationAdapter.notifyDataSetChanged()
        super.onResume()
    }
    fun createViewBitmap(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
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
    @SuppressLint("SetWorldReadable")
    private fun share(view: View,planResponseItem: ElevationResponseItem) {
        val bitmap =  createViewBitmap(view)
        val randomString = UUID.randomUUID().toString().substring(0,15)
        try {
            val file = File(requireActivity().externalCacheDir, "pic_${randomString}.png")
            val outputStrem = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStrem)
            outputStrem.flush()
            outputStrem.close()
            file.setReadable(true, false)
            val imageuri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.homefolder.example.provider.archplanner",
                file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val text = "${planResponseItem.title} Hey, Please check this app" + " https://play.google.com/store/apps/details?id=" + requireActivity().packageName
            intent.putExtra(Intent.EXTRA_TEXT,text)
            intent.putExtra(Intent.EXTRA_STREAM,imageuri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "share by"))
        }catch (e: Exception){
            Toast.makeText(requireContext(),"${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}