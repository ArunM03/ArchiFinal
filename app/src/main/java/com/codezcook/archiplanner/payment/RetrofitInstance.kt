package com.codezcook.archiplanner.payment

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitInstance {
    interface RetrofitInterface {

        @POST("/getOrderId")
        fun getOrderId(@Body map : HashMap<String,String>) : Call<Order>

        @POST("/updateTransactionStatus")
        fun updateTransaction(@Body map : HashMap<String,String>) : Call<String>
    }
}