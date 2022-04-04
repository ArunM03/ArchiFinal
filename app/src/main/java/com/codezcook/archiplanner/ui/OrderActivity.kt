package com.codezcook.archiplanner.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_getdetails.*
import kotlinx.android.synthetic.main.rv_plans.view.*

class OrderActivity : AppCompatActivity() {

    lateinit var viewmodel : PlanViewmodel
    var messageGmail : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_order)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        viewmodel.getOrderPage()

        bt_send.setOnClickListener {
            val name = ed_username.text.toString()
            val phone1 = ed_mobilenumber.text.toString()
            val phone2 = ed_mobilenumber2.text.toString()
            val email = ed_email.text.toString()
            val address = ed_address.text.toString()
            val needs = ed_needs.text.toString()
            if (name.isNotEmpty() && phone1.isNotEmpty() && phone2.isNotEmpty() && email.isNotEmpty()
                && address.isNotEmpty() && needs.isNotEmpty()){
                viewmodel.sendOrderDetails(name,phone1, phone2, email, address, needs)
                val message = "Name : $name\nPhone1 : $phone1\nPhone2 : $phone2\n" +
                        "Email : $email\nAddress : $address\n" +
                        "Needs : $needs\n"
                messageGmail = message
            }else{
                Toast.makeText(this,"Please enter all details",Toast.LENGTH_SHORT).show()
            }
        }


        ib_close.setOnClickListener {
            ct_senddetails.visibility = View.VISIBLE
            iv_order.visibility = View.INVISIBLE
            ib_close.visibility = View.INVISIBLE
        }

        viewmodel.orderpageLive.observe(this, Observer {

            val url = "https://admin.archiplanner.in/storage/app/public/${it[0].folder}/${it[0].image}"
         //   Toast.makeText(this,url,Toast.LENGTH_SHORT).show()
            Glide.with(this).load(url).into(iv_order)
        })

        viewmodel.sendOrderDetailsLive.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            //sendEmail("codezcookarchi@gmail.com",messageGmail)
        })

        viewmodel.errororderpageLive.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

        viewmodel.errorSendOrderDetails.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

    }

    fun sendEmail(email : String,message : String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_TEXT,message)
        intent.setType("message/rfc822")
        startActivity(Intent.createChooser(intent,"Choose an email client"))
    }

}