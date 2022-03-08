package com.codezcook.archiplanner.ui.dashboard.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codezcook.archiplanner.adapter.PlanAdapter
import com.codezcook.archiplanner.adapter.PlanFavAdapter
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.data.PlanResponseItem
import com.codezcook.archiplanner.data.SavePlanResponseItemItem
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.fragment_favourites.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    lateinit var viewmodel : PlanViewmodel
    val adapter = PlanFavAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        var planlist = listOf<SavePlanResponseItemItem>()

        rv_favt.adapter = adapter
        rv_favt.layoutManager = LinearLayoutManager(requireContext())

        adapter.unSave {
            viewmodel.unSaveFavtPost("0",it.planid)
            viewmodel.getFavtPlan("0")
            adapter.notifyDataSetChanged()
        }
        viewmodel.errorunsavePlanLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })
        viewmodel.unSavePlanLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Successfully removed",Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        })

        adapter.share {
            share(it.view, it.planResponseItem)
        }

        viewmodel.getFavtPlan("0")

        viewmodel.getFavtPlanLive.observe(viewLifecycleOwner, Observer {
            adapter.planlist = it
            planlist = it
            adapter.notifyDataSetChanged()
        })



    }
    @SuppressLint("SetWorldReadable")
    private fun share(view: View,planResponseItem: SavePlanResponseItemItem) {
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
            val text = "${planResponseItem.name} Hey, Please check this app" + " https://play.google.com/store/apps/details?id=" + requireActivity().packageName
            intent.putExtra(Intent.EXTRA_TEXT,text)
            intent.putExtra(Intent.EXTRA_STREAM,imageuri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "share by"))
        }catch (e: Exception){
            Toast.makeText(requireContext(),"${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    fun createViewBitmap(view: View): Bitmap? {
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

}