package com.codezcook.archiplanner.sharedpref

import android.content.Context

class SharedPref(context:  Context) {
    val sharedpref = context.getSharedPreferences("mypref", Context.MODE_PRIVATE)
    val editor = sharedpref.edit()

    val JOINEDDATE = "JoinedDate"
    val FREEPLANSCOUNT = "FreePlansCount"

    val USERID = "UserId"

    fun saveJoinedDate(joinedDate : String){
        sharedpref.edit().apply{
            putString(JOINEDDATE,joinedDate)
            apply()
        }
    }

    fun getUserId() : String?{
        return sharedpref.getString(USERID,"null")
    }

    fun saveUserId(id  : String){
        sharedpref.edit().apply{
            putString(USERID,id)
            apply()
        }
    }

    fun getJoinedDate() : String?{
        return sharedpref.getString(JOINEDDATE,"null")
    }


    fun saveSubscription(endDate : String){
        sharedpref.edit().apply {
            putString("subscriptionend",endDate)
            putBoolean("subscribe",true)
            apply()

        }
    }

    fun addCount(num : Int) {
        sharedpref.edit().apply {
            putInt("count",num)
            apply()
        }
    }

    fun addFixedCount(num : Int) {
        sharedpref.edit().apply {
            putInt("fixedcount",num)
            apply()
        }
    }


    fun addCountFreePlanCount(num : Int) {
        sharedpref.edit().apply {
            putInt(FREEPLANSCOUNT,num)
            apply()
        }
    }

    fun getCountFreePlanCount() : Int {
        return sharedpref.getInt(FREEPLANSCOUNT,0)
    }

    fun getCount() : Int {
        return sharedpref.getInt("count",0)
    }


    fun getFixedCount() : Int {
        return sharedpref.getInt("fixedcount",0)
    }

    fun isSubscribed(): Boolean {
        return sharedpref.getBoolean("subscribe",false)
    }
    fun getSubscriptionEnd() : String?{
       return sharedpref.getString("subscriptionend","null")
    }
    fun unSubscribe(){
        editor.apply {
            putBoolean("subscribe",false)
            apply()
        }
    }
}