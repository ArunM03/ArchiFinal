package com.codezcook.archiplanner.api

import com.codezcook.archiplanner.data.*
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlanInterface {

    @GET("api/plan.php")
    suspend fun getPlans() : Response<PlanResponse>

    @GET("api/elevation.php")
    suspend fun getElevations() : Response<ElevationResponse>

    @GET("/api/admin.php")
    suspend fun getContact() : Response<ContactResponse>

    @GET("/api/count.php")
    suspend fun getFreePlanCount() : Response<PlanCountResponse>

    @GET("/api/ad.php")
    suspend fun getPaginationAd() : Response<PaginationAdResponse>

    @GET("/api/questioncat.php")
    suspend fun getTopicsQA() : Response<TopicData>

    @GET("/api/payment.php")
    suspend fun getPaymentDetails() : Response<PaymentDetailsResponse>


    @GET("/api/price.php")
    suspend fun getOrderPage() : Response<OrderPageResponse>

    @GET("/api/question.php")
    suspend fun getQA(
        @Query("data")
        data : String,
        @Query("data1")
        data1 : String
    ) : Response<QAResponse>

    @POST("/api/search.php")
    suspend fun sendSearchWord(
       @Query("data")
       data : String
    )

    @POST("/api/favplan.php")
    suspend fun saveFavtPlan(
        @Query("data")
        data : String,
        @Query("data1")
        data1 : String
    )

//send
    @POST("/api/order.php")
    suspend fun sendOrderDetails(
        @Query("name")
        name : String,
        @Query("phone1")
        phone1 : String,
        @Query("phone2")
        phone2 : String,
        @Query("mailid")
        mailid : String,
        @Query("address")
        address : String,
        @Query("needs")
        needs : String
    ) : Response<String>


    @POST("/api/users.php")
    suspend fun createUserDetails(
        @Query("auth")
        auth : Int,
        @Query("userid")
        userid : String,
        @Query("subscribe")
        subscribe : String,
        @Query("plan")
        plan : String,
        @Query("edate")
        edate : String,
        @Query("sdate")
        sdate : String,
        @Query("name")
        name : String,
        @Query("phone")
        phone : String,
        @Query("mail")
        mail : String
    ) : Response<String>

    @GET("/api/is_sub.php")
    suspend fun getUserDetails(
        @Query("auth")
        auth : Int,
        @Query("userid")
        userid : String,
        @Query("subscribe")
        subscribe : String,
        @Query("plan")
        plan : String,
        @Query("edate")
        edate : String,
        @Query("sdate")
        sdate : String,
        @Query("name")
        name : String,
        @Query("phone")
        phone : String,
        @Query("mail")
        mail : String
    ) : Response<String>


    @POST("/api/users.php")
    suspend fun updatePaymentDetails(
        @Query("auth")
        auth : Int,
        @Query("userid")
        userid : String,
        @Query("subscribe")
        subscribe : String,
        @Query("plan")
        plan : String,
        @Query("edate")
        edate : String,
        @Query("sdate")
        sdate : String,
        @Query("elong")
        elong : String,
        @Query("slong")
        slong : String
    ) : Response<String>

  @POST("/api/favelevation.php")
    suspend fun saveFavtElevation(
        @Query("data")
        data : String,
        @Query("data1")
        data1 : String
    )

    @POST("/api/pfav.php")
    suspend fun getFavtPlan(
        @Query("data")
        data : String
    ) : Response<SavePlanResponseItem>

    @POST("/api/efav.php")
    suspend fun getFavtElevation(
        @Query("data")
        data : String
    ) : Response<SavePlanResponseItem>

    @POST("/api/favplandel.php")
    suspend fun unSaveFavtPlan(
        @Query("data")
        data : String,
        @Query("data1")
        data1 : String
    )

    @POST("/api/faveledel.php")
    suspend fun unSaveFavtElevation(
        @Query("data")
        data : String,
        @Query("data1")
        data1 : String
    )

}