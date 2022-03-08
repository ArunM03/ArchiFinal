package com.codezcook.archiplanner.api

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codezcook.archiplanner.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PlanViewmodel :  ViewModel() {

    var planLive : MutableLiveData<List<PlanResponseItem>> = MutableLiveData()
    var errorPlanLive : MutableLiveData<String> = MutableLiveData()
    var elevationLive : MutableLiveData<List<ElevationResponseItem>> = MutableLiveData()
    var errorElevationLive : MutableLiveData<String> = MutableLiveData()
    var contactLive : MutableLiveData<List<ContactResponseItem>> = MutableLiveData()
    var errorContactLive : MutableLiveData<String> = MutableLiveData()
    var qaLive : MutableLiveData<List<QAResponseItem>> = MutableLiveData()
    var errorQaLive : MutableLiveData<String> = MutableLiveData()
    var topicLive : MutableLiveData<List<TopicDataItem>> = MutableLiveData()
    var errorTopicLive : MutableLiveData<String> = MutableLiveData()
    var orderpageLive : MutableLiveData<List<OrderPageResponseItem>> = MutableLiveData()
    var errororderpageLive : MutableLiveData<String> = MutableLiveData()
    var errorSearch : MutableLiveData<String> = MutableLiveData()
    var errorSaveFavtPlan : MutableLiveData<String> = MutableLiveData()
    var errorGetFavtPlan : MutableLiveData<String> = MutableLiveData()
    var errorgetFavtPlanLive : MutableLiveData<String> = MutableLiveData()
    var errorunsavePlanLive : MutableLiveData<String> = MutableLiveData()
    var errorSendOrderDetails : MutableLiveData<String> = MutableLiveData()
    var sendOrderDetailsLive : MutableLiveData<String> = MutableLiveData()
    var unSavePlanLive : MutableLiveData<String> = MutableLiveData()
    var getFavtPlanLive: MutableLiveData<List<SavePlanResponseItemItem>> = MutableLiveData()
    var getFavtElevationLive: MutableLiveData<List<SaveElevationResponseItem>> = MutableLiveData()

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseFirestore = FirebaseFirestore.getInstance()

    val users = "users"

    val userCollection = firebaseFirestore.collection(users)

    var errorCreateUserDetailsLive : MutableLiveData<String> = MutableLiveData()

    var paymentDetailsLive : MutableLiveData<List<PaymentDetailsResponseItem>> = MutableLiveData()
    var errorPaymentDetailsLive : MutableLiveData<String> = MutableLiveData()

    var createUserResponseLive : MutableLiveData<String> = MutableLiveData()
    var updateUserResponseLive : MutableLiveData<String> = MutableLiveData()
    var errorUpdateUserResponseLive : MutableLiveData<String> = MutableLiveData()

    var countLive : MutableLiveData<PlanCountResponse> = MutableLiveData()

    var paginationAdLive : MutableLiveData<PaginationAdResponseItem> = MutableLiveData()



    private var _userCreatedLive = MutableLiveData<String>()
    var userCreatedLive : LiveData<String> = _userCreatedLive
    private var _errorUserCreatedLive = MutableLiveData<String>()
    var errorUserCreatedLive : LiveData<String> = _errorUserCreatedLive



    fun createPlayer(userData: UserData) =  viewModelScope.launch(Dispatchers.IO) {
        try {
            userCollection.document(userData.id).get().addOnSuccessListener {
                if (!it.exists()) {
                    userCollection.document(userData.id).set(userData).addOnSuccessListener {
                        _userCreatedLive.postValue("Success")
                    }.addOnFailureListener {
                        _errorUserCreatedLive.postValue(it.message)
                    }
                } else {
                    _userCreatedLive.postValue("Success")
                }
            }.addOnFailureListener {
                _errorUserCreatedLive.postValue(it.message)
            }
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            _errorUserCreatedLive.postValue(e.message)
        }
    }

    private var _myProfileLive = MutableLiveData<UserData>()
    var myProfileLive : LiveData<UserData> = _myProfileLive
    private var _errorMyProfileLive = MutableLiveData<String>()
    var errorMyProfileLive : LiveData<String> = _errorMyProfileLive

    fun getMyProfileData(id : String) = viewModelScope.launch(Dispatchers.IO){

        try {
            val data =  userCollection.document(id).get().await().toObject(UserData::class.java)

            data.let {
                _myProfileLive.postValue(it)
            }

        }catch (e: Exception){
            Firebase.crashlytics.recordException(e)
            _errorMyProfileLive.postValue(e.message)
        }

    }


    private var _myUpdatedProfileLive = MutableLiveData<String>()
    var myUpdatedProfileLive : LiveData<String> = _myUpdatedProfileLive
    private var _errorMyUpdatedProfileLive = MutableLiveData<String>()
    var errorMyUpdatedProfileLive : LiveData<String> = _errorMyUpdatedProfileLive

    fun updateProfileData(userData: UserData) = viewModelScope.launch(Dispatchers.IO){

        try {
            userCollection.document(userData.id).set(userData).addOnSuccessListener {
                _myUpdatedProfileLive.postValue("Updated")
            }
        }catch (e: Exception){
            Firebase.crashlytics.recordException(e)
            _errorMyProfileLive.postValue(e.message)
        }

    }



    fun  getPaginationAd() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getPaginationAd()
            if (response.isSuccessful){
                response.body()?.let {
                    if (it.isNotEmpty()){
                        paginationAdLive.postValue(it[0])
                    }
                }
            }else{
//                errorPlanLive.postValue("Error")
            }
        }catch (e:Exception){
//            errorPlanLive.postValue(e.message)
        }
    }


    fun  getFreePlanCount() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getFreePlanCount()
            if (response.isSuccessful){
                response.body()?.let {
                    if (it.isNotEmpty()){
                        countLive.postValue(it)
                    }
                }
            }else{
//                errorPlanLive.postValue("Error")
            }
        }catch (e:Exception){
//            errorPlanLive.postValue(e.message)
        }
    }


    fun getPlans() = viewModelScope.launch(Dispatchers.IO) {
        try {
        val response = RetrofitInstance.api.getPlans()
        if (response.isSuccessful){
            response.body()?.let {
                val plansDetails = it.map {
                    PlanResponseItem(it.created_at,it.facing,it.id,it.images,it.name,it.room,it.status,it.tags,it.updated_at)
                }
                planLive.postValue(plansDetails.filter { it.status == "active" })
            }
        }else{
            errorPlanLive.postValue("Error")
        }
        }catch (e:Exception){
            errorPlanLive.postValue(e.message)
        }
    }


    fun getPaymentDetails() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getPaymentDetails()
            if (response.isSuccessful){
                response.body()?.let {
                    val paymentDetails = it.map {
                        PaymentDetailsResponseItem(it.id,it.name,it.price,it.count)
                    }
                    paymentDetailsLive.postValue(paymentDetails)
                }
            }else{
                errorPaymentDetailsLive.postValue("Error")
            }
        }catch (e:Exception){
            errorPaymentDetailsLive.postValue(e.message)
        }
    }


    fun getElevations() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getElevations()
            if (response.isSuccessful){
                response.body()?.let {
                    val elevationDetails = it.map {
                        ElevationResponseItem(it.floor,it.folder,it.id,it.image,it.title)
                    }
                    elevationLive.postValue(elevationDetails)
                }
            }else{
                errorElevationLive.postValue("Error")
            }
        }catch (e:Exception){
            errorElevationLive.postValue(e.message)
        }
    }

    fun getContact() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getContact()
            if (response.isSuccessful){
                response.body()?.let {
                    val contactDetails = it.map {
                        ContactResponseItem(it.email,it.location,it.name,it.phone_number,it.phone_number1,it.services)
                    }
                    contactLive.postValue(contactDetails)
                }
            }else{
                errorContactLive.postValue("Error")
            }
        }catch (e:Exception){
            errorContactLive.postValue(e.message)
        }
    }

    fun getOrderPage() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getOrderPage()
            if (response.isSuccessful){
                response.body()?.let {
                    val contactDetails = it.map {
                 OrderPageResponseItem(it.folder,it.image)
                   }
                    orderpageLive.postValue(contactDetails)
                }
            }else{
                errororderpageLive.postValue("Error")
            }
        }catch (e:Exception){
            errororderpageLive.postValue(e.message)
        }
    }
    fun getTopics() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getTopicsQA()
            if (response.isSuccessful){
                response.body()?.let {
                    val topicDetails = it.map {
                        TopicDataItem(it.created_at,it.id,it.name,it.updated_at)
                    }
                    topicLive.postValue(topicDetails)
                }
            }else{
                errorTopicLive.postValue("Error")
            }
        }catch (e:Exception){
            errorTopicLive.postValue(e.message)
        }
    }
    fun sendSearchWord(word : String) =  viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.sendSearchWord(word)
        }catch (e:Exception){
            errorSearch.postValue(e.message)
        }
    }

    fun saveFavtPost(data : String,data1 : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.saveFavtPlan(data,data1)
        }catch (e:Exception){
            errorSaveFavtPlan.postValue(e.message)
        }
    }

    fun sendOrderDetails(name : String,phone1 : String,phone2 : String,email : String,address : String,needs : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.sendOrderDetails(name,phone1,phone2,email,address,needs)
            if (response.isSuccessful){
                sendOrderDetailsLive.postValue(response.body())
            }else{
                errorSendOrderDetails.postValue("Unsuccessful")
            }
        }catch (e:Exception){
            errorSendOrderDetails.postValue(e.message)
        }
    }

    fun createUserDetails(userid : String, name : String,phone : String,email : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.createUserDetails(0,userid,"0","","","",name,phone,email)
          //  val response = RetrofitInstance.api.createUserDetails(userid,"arun")
            if (response.isSuccessful){
                createUserResponseLive.postValue(response.body())
            }else{
                createUserResponseLive.postValue(response.body())
            }
        }catch (e:Exception){
            errorCreateUserDetailsLive.postValue(e.message)
        }
    }

    fun updatePaymentDetails(userid : String,auth : Int, subscribe : String,plan : String,sdate : String,edate : String,elong : String, slong : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.updatePaymentDetails(auth,userid,subscribe,plan,edate, sdate,elong, slong)
            if (response.isSuccessful){
                updateUserResponseLive.postValue(response.body())
            }else{
                updateUserResponseLive.postValue(response.body())
            }

        }
        catch (e:Exception){
            errorUpdateUserResponseLive.postValue(e.message)
        }
    }



    fun unSaveFavtPost(data : String,data1 : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.unSaveFavtPlan(data,data1)
            unSavePlanLive.postValue("Success")
        }catch (e:Exception){
            errorunsavePlanLive.postValue(e.message)
        }
    }
    fun getFavtPlan(data : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getFavtPlan(data)
            if (response.isSuccessful){
                response.body()?.let {
                    val savePlanResponseItem = it.map {
                        SavePlanResponseItemItem(it.created_at,it.facing,it.id,it.images,it.name,it.planid,it.room,it.status,it.tags,it.updated_at,it.userid)
                    }
                    getFavtPlanLive.postValue(savePlanResponseItem)
                }
            }else{
                errorgetFavtPlanLive.postValue("Error")
            }
        }catch (e:Exception){
            errorgetFavtPlanLive.postValue(e.message)
        }
    }
    fun saveFavtElevation(data : String,data1 : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.saveFavtElevation(data,data1)
        }catch (e:Exception){
            errorSaveFavtPlan.postValue(e.message)
        }
    }
    fun unSaveFavtElevation(data : String,data1 : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.unSaveFavtElevation(data,data1)
            unSavePlanLive.postValue("Success")
        }catch (e:Exception){
            errorunsavePlanLive.postValue(e.message)
        }
    }
    fun getQA(catId : String,data1 : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getQA(catId,data1)
            if (response.isSuccessful){
                response.body()?.let {
                    val qaResponseItem = it.map {
                       QAResponseItem(it.answer,it.created_at,it.faq_category_id,it.id,it.option1,it.option2,it.option3,it.option4,it.question,it.updated_at)
                    }
                    qaLive.postValue(qaResponseItem)
                }
            }else{
                errorQaLive.postValue("Error")
            }
        }catch (e:Exception){
            errorQaLive.postValue(e.message)
        }
    }
    fun getFavtElevation(data : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getFavtElevation(data)
            if (response.isSuccessful){
                response.body()?.let {
                    val savePlanResponseItem = it.map {
                        SavePlanResponseItemItem(it.created_at,it.facing,it.id,it.images,it.name,it.planid,it.room,it.status,it.tags,it.updated_at,it.userid)
                    }
                    getFavtPlanLive.postValue(savePlanResponseItem)
                }
            }else{
                errorgetFavtPlanLive.postValue("Error")
            }
        }catch (e:Exception){
            errorgetFavtPlanLive.postValue(e.message)
        }
    }

}