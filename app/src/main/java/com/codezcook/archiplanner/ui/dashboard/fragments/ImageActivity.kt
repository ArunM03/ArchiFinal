package com.codezcook.archiplanner.ui.dashboard.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner2.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.fragment_grey.*
import kotlinx.android.synthetic.main.fragment_model.*

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        if (Constants.isModel23) {
            if (!Constants.isSubscribed) {
                Glide.with(this).load(Constants.url)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(32)))
                    .into(touchimage_iv)
            } else {
                Glide.with(this).load(Constants.url).into(touchimage_iv)
            }
        } else {
            Glide.with(this).load(Constants.url).into(touchimage_iv)
        }



    }
}