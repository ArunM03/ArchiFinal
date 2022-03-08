package com.codezcook.archiplanner

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_order.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_main)


        val url = "https://admin.archiplanner.in/storage/app/public/${Constants.paginationAd}"
        Glide.with(this).load(url).into(iv_order_2)

        ib_close_2.setOnClickListener {
            onBackPressed()
            finish()
        }

    }
}