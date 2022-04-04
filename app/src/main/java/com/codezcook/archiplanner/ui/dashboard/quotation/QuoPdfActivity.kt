package com.codezcook.archiplanner.ui.dashboard.quotation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.codezcook.archiplanner.adapter.VpAdapter
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.fragment_vp.*

class QuoPdfActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_quo_pdf)

    }
}