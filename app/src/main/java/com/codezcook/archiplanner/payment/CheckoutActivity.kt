package com.codezcook.archiplanner.payment

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.sharedpref.SharedPref
import com.codezcook.archiplanner2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class CheckoutActivity : AppCompatActivity() , PaymentResultWithDataListener {
    lateinit var retrofit: Retrofit
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var retrofitInterface: RetrofitInstance.RetrofitInterface
    lateinit var viewmodel : PlanViewmodel
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)


        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)


        viewmodel.myUpdatedProfileLive.observe(this, androidx.lifecycle.Observer {
            Constants.userData.apply {
                    this.plan = "Premium"
                    this.TotalCount = this.TotalCount + Constants.count.toInt()
            }
            Toast.makeText(this,"$it", Toast.LENGTH_LONG).show()
            onBackPressed()
        })

        viewmodel.updateUserResponseLive.observe(this, androidx.lifecycle.Observer {
            val userDate = Constants.userData
            userDate.apply {
                this.plan = "Premium"
                this.TotalCount = this.TotalCount + Constants.count.toInt()
            }
            viewmodel.updateProfileData(userDate)

        })

        viewmodel.errorUpdateUserResponseLive.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            onBackPressed()
        })


        Checkout.preload(applicationContext)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Payment process ... Please wait ")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)


        val gson = GsonBuilder().setLenient()

        retrofit = Retrofit.Builder()
            .baseUrl("https://tranquil-coast-36107.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson.create()))
            .build()

        retrofitInterface = retrofit.create(RetrofitInstance.RetrofitInterface::class.java)

         //getOrderId(Constants.amount)
        getOrderId("1")


    }

    private fun getOrderId(amount: String) {
        val map = HashMap<String,String>()
        map["amount"] = amount
        retrofitInterface.getOrderId(map).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.body() != null){
                    initiatePayment(amount, response.body()!!)
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(this@CheckoutActivity,"Payment failed ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initiatePayment(amount: String, body: Order) {
        val checkout = Checkout()
        checkout.setKeyID(body.getKeyId())
        checkout.setImage(R.mipmap.logo_round)


        val paymentOptions = JSONObject()
        paymentOptions.put("name", "${Constants.userData.username}")
        paymentOptions.put("amount", amount)
        paymentOptions.put("order_id", body.getOrderId())
        paymentOptions.put("currency", "INR")
        paymentOptions.put("description", "Archiplanner app premium feature")

        checkout.open(this,paymentOptions)
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        val map = HashMap<String,String>()
        map["order_id"] = p1!!.orderId
        map["pay_id"] = p1!!.paymentId
        map["signature"] = p1!!.signature

        retrofitInterface.updateTransaction(map)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body().equals("success")){
                        val calendar = Calendar.getInstance().time
                        val sharedPref = SharedPref(this@CheckoutActivity)
                        sharedPref.saveSubscription(calendar.time.toString())
                        val curFixedCount = sharedPref.getFixedCount()
                        sharedPref.addFixedCount(Constants.count.toInt() + curFixedCount)
                    //    sharedPref.addCount(0)
                        viewmodel.updatePaymentDetails(firebaseAuth.currentUser?.uid!!,1,"1",Constants.plan,"${calendar}","${calendar}","${calendar.time}","${calendar.time}")
                        Toast.makeText(this@CheckoutActivity,"Payment succeeded",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@CheckoutActivity,"Payment Failed ${t.message}",Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

        Toast.makeText(this,"Payment failed $p0", Toast.LENGTH_SHORT).show()

    }



}