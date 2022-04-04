package com.codezcook.archiplanner.ui.dashboard.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
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
import kotlinx.android.synthetic.main.fragment_planview.*
import org.w3c.dom.Text
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*


@Suppress("DEPRECATION")
class PlanViewActivity : AppCompatActivity() {


    lateinit var viewmodel : PlanViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_plan_view)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)



        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.greyFragment, R.id.colorFragment, R.id.objectFragment))

        nav_view.setupWithNavController(findNavController(R.id.fragmentview))


    }


}