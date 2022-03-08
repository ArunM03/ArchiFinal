package com.codezcook.archiplanner.dialog

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.payment.CheckoutActivity
import com.codezcook.archiplanner2.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomDialog(val context: Context) {


    fun createBottomDialogFromActivity(activity : AppCompatActivity){
        val dialog = BottomSheetDialog(context)
        val customview = activity.layoutInflater.inflate(R.layout.layout_subsciption,null,false)

        val cd_1 = customview.findViewById<CardView>(R.id.cd_yearly)
        val cd_2= customview.findViewById<CardView>(R.id.cd_month3)
        val cd_3 = customview.findViewById<CardView>(R.id.cd_month1)
        val tvpack_1 = customview.findViewById<TextView>(R.id.tv_plan_yearly)
        val tvpack_2 = customview.findViewById<TextView>(R.id.tv_plan_monthly3)
        val tvpack_3 = customview.findViewById<TextView>(R.id.tv_plan_monthly1)
        val tvprice_1 = customview.findViewById<TextView>(R.id.tv_price_yearly)
        val tvprice_2 = customview.findViewById<TextView>(R.id.tv_price_monthly3)
        val tvprice_3 = customview.findViewById<TextView>(R.id.tv_price_monthly1)
        val tvplan_1 = customview.findViewById<TextView>(R.id.tv_plan_1)
        val tvplan_2 = customview.findViewById<TextView>(R.id.tv_plan_2)
        val tvplan_3 = customview.findViewById<TextView>(R.id.tv_plan_3)
        val paymentList = Constants.paymentDetails
        if (paymentList.size == 3){
            tvpack_1.text = paymentList[0].name
            tvprice_1.text = "₹" + paymentList[0].price
            tvplan_1.text = paymentList[0].count
            tvpack_2.text = paymentList[1].name
            tvprice_2.text = "₹" +  paymentList[1].price
            tvplan_2.text = paymentList[1].count
            tvpack_3.text = paymentList[2].name
            tvprice_3.text = "₹" +  paymentList[2].price
            tvplan_3.text = paymentList[2].count
            cd_1.setOnClickListener {
                Constants.plan = paymentList[0].name
                Constants.amount = paymentList[0].price
                Constants.count = paymentList[0].count
                goToCheckoutActivity(activity)
            }
            cd_2.setOnClickListener {
                Constants.plan = paymentList[1].name
                Constants.amount = paymentList[1].price
                Constants.count = paymentList[1].count
                goToCheckoutActivity(activity)
            }
            cd_3.setOnClickListener {
                Constants.plan = paymentList[2].name
                Constants.amount = paymentList[2].price
                Constants.count = paymentList[2].count
                goToCheckoutActivity(activity)
            }
        }
        dialog.setContentView(customview)
        dialog.show()
    }

    fun createBottomDialog(fragment: Fragment){
        val dialog = BottomSheetDialog(context)
        val customview = fragment.layoutInflater.inflate(R.layout.layout_subsciption,null,false)

        val cd_1 = customview.findViewById<CardView>(R.id.cd_yearly)
        val cd_2= customview.findViewById<CardView>(R.id.cd_month3)
        val cd_3 = customview.findViewById<CardView>(R.id.cd_month1)
        val tvpack_1 = customview.findViewById<TextView>(R.id.tv_plan_yearly)
        val tvpack_2 = customview.findViewById<TextView>(R.id.tv_plan_monthly3)
        val tvpack_3 = customview.findViewById<TextView>(R.id.tv_plan_monthly1)
        val tvprice_1 = customview.findViewById<TextView>(R.id.tv_price_yearly)
        val tvprice_2 = customview.findViewById<TextView>(R.id.tv_price_monthly3)
        val tvprice_3 = customview.findViewById<TextView>(R.id.tv_price_monthly1)
        val tvplan_1 = customview.findViewById<TextView>(R.id.tv_plan_1)
        val tvplan_2 = customview.findViewById<TextView>(R.id.tv_plan_2)
        val tvplan_3 = customview.findViewById<TextView>(R.id.tv_plan_3)
        val paymentList = Constants.paymentDetails
        if (paymentList.size == 3){
            tvpack_1.text = paymentList[0].name
            tvprice_1.text = "₹" + paymentList[0].price
            tvplan_1.text = paymentList[0].count
            tvpack_2.text = paymentList[1].name
            tvprice_2.text = "₹" +  paymentList[1].price
            tvplan_2.text = paymentList[1].count
            tvpack_3.text = paymentList[2].name
            tvprice_3.text = "₹" +  paymentList[2].price
            tvplan_3.text = paymentList[2].count
            cd_1.setOnClickListener {
                Constants.plan = paymentList[0].name
                Constants.amount = paymentList[0].price
                Constants.count = paymentList[0].count
                goToCheckoutActivity(fragment)
            }
            cd_2.setOnClickListener {
                Constants.plan = paymentList[1].name
                Constants.amount = paymentList[1].price
                Constants.count = paymentList[1].count
                goToCheckoutActivity(fragment)
            }
            cd_3.setOnClickListener {
                Constants.plan = paymentList[2].name
                Constants.amount = paymentList[2].price
                Constants.count = paymentList[2].count
                goToCheckoutActivity(fragment)
            }
        }
        dialog.setContentView(customview)
        dialog.show()
    }

    private  fun goToCheckoutActivity(fragment: Fragment){
        fragment.startActivity(Intent(context,CheckoutActivity::class.java))
    }

    private  fun goToCheckoutActivity(activity: AppCompatActivity){
        activity.startActivity(Intent(context,CheckoutActivity::class.java))
    }

}