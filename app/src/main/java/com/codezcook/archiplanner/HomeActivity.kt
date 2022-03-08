package com.codezcook.archiplanner

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.data.SaveElevationResponseItem
import com.codezcook.archiplanner.data.SavePlanResponseItemItem
import com.codezcook.archiplanner.dialog.BottomDialog
import com.codezcook.archiplanner.sharedpref.SharedPref
import com.codezcook.archiplanner.ui.OrderActivity
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var viewmodel : PlanViewmodel
    private var mInterstitialAd: InterstitialAd? = null
    private var mInterstitialAd2: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_home)

        MobileAds.initialize(this)

        //managePayment()

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,getString(R.string.adevery20min_interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                if (!Constants.isSubscribed) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(this@HomeActivity)
                    }
                }
            }
        })
        InterstitialAd.load(this,getString(R.string.adevery10min_interstitial_poster), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd2 = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd2 = interstitialAd
            }
        })

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        val countForUser = SharedPref(this).getCount()
        val countForFixed = SharedPref(this).getFixedCount()

        Constants.curCount = countForUser
        Constants.curFixedCount = countForFixed

     //   Toast.makeText(this, "curCount ${countForUser} and fixedCount $countForFixed", Toast.LENGTH_SHORT).show()

        if (Constants.curCount >= Constants.curFixedCount) {
            Constants.downloadType = Constants.FREEDOWNLOAD
        } else {
            Constants.downloadType =  Constants.PAIDDOWNLOAD
        }

        Constants.isSubscribed =  SharedPref(this).isSubscribed()

        viewmodel.countLive.observe(this, Observer {

            Constants.serverCount = it[0].price.toString().toInt()

            val count = it[0].count.toString().toInt()


            Constants.userCount = countForUser

            if (count <= Constants.userCount) {
                Constants.isFreeUser = true
            }

            if (Constants.serverCount <= Constants.userCount){
             //  Constants.isSubscribed = true
                Constants.isFreeUser = true
            //    Toast.makeText(this,"Executing",Toast.LENGTH_SHORT).show()
            }
        })

        viewmodel.getFreePlanCount()

        setSupportActionBar(toolbar)

        if (Constants.isDownload){
            nav_host_fragment_content_home.findNavController().navigate(R.id.planVPFragment)
        }

        fab_order.setOnClickListener {
        startActivity(Intent(this,OrderActivity::class.java))
        }

        setCountDownTimerFor10min()
        setCountDownTimerFor20min()

        

        viewmodel.getFavtPlan("0")
        viewmodel.getFavtElevation("0")

        viewmodel.getContact()

        getPaymentDetails()


        viewmodel.contactLive.observe(this, Observer { it ->
            if (!Constants.contactUpdated){
                Constants.contactDetails = it[0]
                Constants.customerDetails = it[0]
            }
        })

        viewmodel.getFavtPlanLive.observe(this, Observer {
            Constants.favtIds = getFavtPlanIdsList(it).toMutableList()
        })
        viewmodel.getFavtElevationLive.observe(this, Observer {
            Constants.favtElevationIds = getFavtElevationIdsList(it).toMutableList()
        })

        ib_allfavts.setOnClickListener {
            nav_host_fragment_content_home.findNavController().navigate(R.id.favouritesFragment)
        }

        viewmodel.errorGetFavtPlan.observe(this, Observer {
            Toast.makeText(this,"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })


        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)


        findNavController(R.id.nav_host_fragment_content_home).addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
               R.id.planListFragment, R.id.planViewFragment,R.id.planVPFragment,R.id.elevationVPFragment-> {
                   hideToolbar()
                   hideOrder()
               }
                    R.id.QAStudyFragment,R.id.QATestFragment,R.id.resultFragment,R.id.topicFragment -> {
                        hideOrder()
                        showToolbar()
                    }
                else -> {
                    showOrder()
                    showToolbar()
                }

            }
        }


        nav_view.menu.findItem(R.id.buy_premium).setOnMenuItemClickListener {
            showPayment()
            true
        }
    }


    fun showPayment(){
        val bottomSheet = BottomDialog(this)
        bottomSheet.createBottomDialogFromActivity(this)
    }

    fun managePayment() {
        val sharedpref  = SharedPref(this)
        val todayTime = Calendar.getInstance().time.time
        val endTime = sharedpref.getSubscriptionEnd()
        endTime?.let {
            if (endTime != "null") {
                val endLong = endTime.toLong()
                if (endLong <= todayTime) {
                    sharedpref.unSubscribe()
                }else{
                    Constants.isSubscribed = true
                }
            }
        }
    }

    fun getPaymentDetails(){
        viewmodel.getPaymentDetails()

        viewmodel.paymentDetailsLive.observe(this, Observer {
            Constants.paymentDetails = it
        })

        viewmodel.errorPaymentDetailsLive.observe(this, Observer {
            Toast.makeText(this,"Something went wrong : $it",Toast.LENGTH_LONG).show()
        })
    }

    fun getFavtPlanIdsList(list : List<SavePlanResponseItemItem>) : List<String>{
        var newlist = mutableListOf<String>()
        for (savePlanResponseItem in list){
            newlist.add(savePlanResponseItem.planid)
        }
        return newlist
    }
    fun getFavtElevationIdsList(list : List<SaveElevationResponseItem>) : List<String>{
        var newlist = mutableListOf<String>()
        for (savePlanResponseItem in list){
            newlist.add(savePlanResponseItem.elevationid)
        }
        return newlist
    }

    fun hideOrder() {
        fab_order.visibility = View.GONE
    }
    fun showOrder() {
        fab_order.visibility = View.VISIBLE
    }

    fun hideToolbar(){
        toolbar.visibility = View.GONE
    }

    fun showToolbar(){
        toolbar.visibility = View.VISIBLE
    }

    fun setCountDownTimerFor20min(){
        val mins = 20*60*1000
        var timer = object: CountDownTimer(mins.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                setCountDownTimerFor20min()
                if (!Constants.isSubscribed) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(this@HomeActivity)
                    }
                }
            }
        }
        timer.start()
    }
    fun setCountDownTimerFor10min(){
        val mins = 10*60*1000
        var timer = object: CountDownTimer(mins.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                setCountDownTimerFor10min()
                if (mInterstitialAd2 != null) {
                    mInterstitialAd2?.show(this@HomeActivity)
                }
            }
        }
        timer.start()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}