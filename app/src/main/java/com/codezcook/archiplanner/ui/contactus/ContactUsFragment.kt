package com.codezcook.archiplanner.ui.contactus


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.fragment_contactus.*


class ContactUsFragment : Fragment(R.layout.fragment_contactus) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        con1.text = com.codezcook.archiplanner.Constants.contactDetails.phone_number
        con2.text = com.codezcook.archiplanner.Constants.contactDetails.phone_number1
    }
}