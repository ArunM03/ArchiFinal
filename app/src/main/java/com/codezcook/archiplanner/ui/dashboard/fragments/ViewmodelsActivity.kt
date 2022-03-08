package com.codezcook.archiplanner.ui.dashboard.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.fragment_planview.*
import org.w3c.dom.Text
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*


@Suppress("DEPRECATION")
class ViewmodelsActivity : AppCompatActivity() {

    var isFABOpen = false
    lateinit var viewmodel : PlanViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)




    }


    fun textcolor(isavailable : Boolean,tv : TextView, list : List<EditText>,cd : CardView){
        var defaultcolor = if (isavailable) Constants.textcolor else ContextCompat.getColor(this,R.color.black)
        val colorpicker = AmbilWarnaDialog(this,defaultcolor,object : AmbilWarnaDialog.OnAmbilWarnaListener {
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
        val alertDialog = AlertDialog.Builder(this).create()
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
            startActivity(Intent(this,ViewmodelsActivity::class.java))
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}