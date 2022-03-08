package com.codezcook.archiplanner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import com.codezcook.archiplanner.data.*
import pub.devrel.easypermissions.EasyPermissions

object Constants {

    var curPlan = ""
    val EASTPLAN = "East Facing"
    val WESTPLAN = "West Facing"
    val NORTHPLAN = "North Facing"
    val SOUTHPLAN = "South Facing"

    var viewlist = mutableListOf<View>()

    var serverCount  = 0
    var userCount = 0

    var isFreeUser = false

    var isModel23 = false


    var plan = ""
    var amount = ""
    var count = ""
    var paymentDetails = listOf<PaymentDetailsResponseItem>()

    var storedverificationId = ""

    var url = ""

    var nextCount = 0


    var paginationAd = ""

    var isSubscribed = false

    var downloadType = ""

    var curCount = 0
    var curFixedCount = 0

    val PAIDDOWNLOAD = "Paiddownload"
    val FREEDOWNLOAD = "Freedownload"



    var emailLogin = ""
    var mobileLogin = ""
    var nameLogin = ""

    var UNPAIDUSER = "UnpaidUser"
    var PAID = "PaidUser"


    var curElevation = ""
    val GROUNDELEVATION = "groundfloor"
    val FIRSTELEVATION = "firstfloor"
    val SECONDELEVATION = "secondfloor"
    val THIRDELEVATION = "thirdfloor"
    val FOURTHELEVATION = "fourthfloor"

    var qaCatid = ""

    var topicName = ""

    var totalmarks = ""
    var totalpercentage = ""
    var totalpercent = 0
    var timetaken = ""

    var testTime = 0
    var easyordiff = ""

    var answerlist = mutableListOf<Boolean>()

    var planlist = listOf<PlanResponseItem>()
    var elevationList = listOf<ElevationResponseItem>()

    var favtIds = mutableListOf<String>()
    var favtElevationIds = mutableListOf<String>()

    var curPlanPos = 0
    var curElevationPos = 0

    var userData = UserData()

    var isDownload = false
    var downloadFrom = ""
    val MODELS = "models"
    val PLANS = "plans"

    var views = mutableListOf<View>()
    val viewfilesname = listOf<String>("grey.jpg","model1.jpg","color.jpg","model2.jpg","model3.jpg","object.jpg")
    val viewfilesname2 = listOf<String>("grey.jpg","model1.jpg","color.jpg","object.jpg")
    val viewmodelsname = listOf<String>("model1.jpg","grey.jpg","model2.jpg","color.jpg","object.jpg","model3.jpg")
    val viewmodelsname2 = listOf<String>("model1.jpg","grey.jpg","color.jpg","object.jpg")
    val viewfilespdf = listOf<String>("grey.pdf","model1.pdf","color.pdf","model2.pdf","model3.pdf","object.pdf")
    val viewfilespdf2 = listOf<String>("grey.pdf","model1.pdf","color.pdf","object.pdf")
    val viewmodelspdf = listOf<String>("model1.pdf","grey.pdf","model2.pdf","color.pdf","object.pdf","model3.pdf")
    val viewmodelspdf2 = listOf<String>("model1.pdf","grey.pdf","color.pdf","object.pdf")


    var curPlanResponseItem = PlanResponseItem()
    var curElevationResponseItem = ElevationResponseItem()

    var contactUpdated = false
    var contactDetails = ContactResponseItem()
    var customerDetails = contactDetails

    var textcolor = 0
    var textcolor2 = 0
    var bordercolor = 0
    var textcolor1 = 0
    var textcolor22 = 0
    var bordercolor1 = 0

    fun requestPermissions(activity_host : Activity) {
        if(Constants.hasLocationPermissions(activity_host)) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                activity_host,
                "You need to accept storage permissions to save quotes in this app.",
                0,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            EasyPermissions.requestPermissions(
                activity_host,
                "You need to accept storage permissions to save quotes in this app.",
                0,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    fun hasLocationPermissions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
}