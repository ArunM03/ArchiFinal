package com.codezcook.archiplanner.ui.dashboard.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.dialog.BottomDialog
import com.codezcook.archiplanner.sharedpref.SharedPref
import com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments.ColorFragment
import com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments.GreyFragment
import com.codezcook.archiplanner.ui.dashboard.fragments.viewfragments.ObjectFragment
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_plan_view.*
import kotlinx.android.synthetic.main.fragment_planview.*
import yuku.ambilwarna.AmbilWarnaDialog

@Suppress("DEPRECATION")
class PlanViewFragment(val value : Int = 0) : Fragment(R.layout.fragment_planview) {

    var isFABOpen = false
    private var mInterstitialAd: InterstitialAd? = null
    private var mInterstitialAd2: InterstitialAd? = null

    lateinit var viewmodel : PlanViewmodel

    lateinit var progressDialog: ProgressDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)


        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait,,.. ")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.greyFragment, R.id.colorFragment, R.id.objectFragment))

        Constants.curPlanResponseItem = Constants.planlist[value]

      //  showPayment()
        closeFABMenu()
        closeFABMenu2()
        viewplans()

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),getString(R.string.plandownload_interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        InterstitialAd.load(requireContext(),getString(R.string.editbutton_interstitial_poster), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd2 = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd2 = interstitialAd
            }
        })

        viewmodel.myUpdatedProfileLive.observe(viewLifecycleOwner, Observer {

            progressDialog.dismiss()

            Constants.userData.apply {
                this.curCount = this.curCount + 1
            }

            Constants.curCount = Constants.userData.curCount
            Constants.curFixedCount = Constants.userData.TotalCount

            if (Constants.curCount >= Constants.curFixedCount) {
                Constants.downloadType = Constants.FREEDOWNLOAD
            } else {
                Constants.downloadType = Constants.PAIDDOWNLOAD
            }

            startActivity(Intent(requireContext(),HomeActivity::class.java))

        })


        fab_edit.setOnClickListener {
            if (Constants.downloadType == Constants.FREEDOWNLOAD) {
                    if (mInterstitialAd2 != null) {
                        mInterstitialAd2?.show(requireActivity())
                    }
                Toast.makeText(requireContext(), "Sorry you can't edit, please buy premium", Toast.LENGTH_SHORT).show()
            } else {
                detailsEditDialog(Constants.textcolor != 0, Constants.bordercolor != 0, Constants.textcolor2 != 0)
            }
      }

        fab_viewmodels.setOnClickListener {
            closeFABMenu()
            viewmodels()
           // startActivity(Intent(requireContext(),ViewmodelsActivity::class.java))
        }

        fab_download.setOnClickListener {

            if (Constants.downloadType == Constants.FREEDOWNLOAD) {

                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                }

            }

            val userData = Constants.userData

            userData.apply {
                this.curCount = this.curCount + 1
            }

            Constants.isDownload = true
            Constants.downloadFrom = Constants.PLANS
            Constants.views = listOf<View>().toMutableList()

            progressDialog.show()

            viewmodel.updateProfileData(userData)

        }

        fab_editviewmodel.setOnClickListener {
            if (Constants.downloadType == Constants.FREEDOWNLOAD) {
                    if (mInterstitialAd2 != null) {
                        mInterstitialAd2?.show(requireActivity())
                    }
                Toast.makeText(requireContext(), "Sorry you can't edit, please buy premium", Toast.LENGTH_SHORT).show()
            } else {
                detailsEditDialog(Constants.textcolor != 0, Constants.bordercolor != 0, Constants.textcolor2 != 0)
            }
        }
        fab_planviews.setOnClickListener {
            closeFABMenu2()
            viewplans()
         //   startActivity(Intent(this,PlanViewActivity::class.java))
        }

        fab_downloadviemodel.setOnClickListener {

            if (Constants.downloadType == Constants.FREEDOWNLOAD) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                }
            }

            val userData = Constants.userData

            userData.apply {
                this.curCount = this.curCount + 1
            }


            Constants.isDownload = true
                Constants.downloadFrom = Constants.PLANS
                Constants.views = listOf<View>().toMutableList()

            progressDialog.show()

            viewmodel.updateProfileData(userData)
        }
        fab_mainviewmdoel.setOnClickListener {
            if (isFABOpen){
                closeFABMenu2()
            }else{
                showFABMenu2()
            }
        }

        setUpBottomNavigation()


        fab_mainmenu.setOnClickListener {
            if (isFABOpen){
                closeFABMenu()
            }else{
                showFABMenu()
            }
        }

    }

    fun showPayment(){
        val bottomSheet = BottomDialog(requireContext())
        bottomSheet.createBottomDialog(this)
    }

    fun viewplans(){
        fab_mainviewmdoel.visibility = View.GONE
        fab_download.visibility = View.VISIBLE
        fab_edit.visibility = View.VISIBLE
        fab_viewmodels.visibility = View.VISIBLE
        fab_mainmenu.visibility = View.VISIBLE
        fab_planviews.visibility = View.GONE
        fab_editviewmodel.visibility = View.GONE
        fab_downloadviemodel.visibility = View.GONE
        nav_viewmodel.visibility = View.GONE
        bottomnavigationplanview.visibility = View.VISIBLE
        val fragment1 = this.childFragmentManager.findFragmentById(R.id.fragmentviewmodel) as NavHostFragment
        val fragment2 = this.childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        fragment1.view?.visibility = View.GONE
        fragment2.view?.visibility = View.VISIBLE
    }
    private fun closeFABMenu2() {
        isFABOpen = false
        tv_fab1viewmodel.visibility = View.GONE
        tv_fab2viewmodel.visibility = View.GONE
        tv_fab3viewmodel.visibility = View.GONE
        fab_planviews.animate().translationY(0F)
        tv_fab1viewmodel.animate().translationY(0F)
        fab_editviewmodel.animate().translationY(0F)
        tv_fab2viewmodel.animate().translationY(0F)
        fab_downloadviemodel.animate().translationY(0F)
        tv_fab3viewmodel.animate().translationY(0F)
        fab_mainviewmdoel.setImageResource(R.drawable.ic_action_edit)
    }
    private fun showFABMenu2() {
        isFABOpen = true
        tv_fab1viewmodel.visibility = View.VISIBLE
        tv_fab2viewmodel.visibility = View.VISIBLE
        tv_fab3viewmodel.visibility = View.VISIBLE
        fab_planviews.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        tv_fab1viewmodel.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fab_editviewmodel.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        tv_fab2viewmodel.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        fab_downloadviemodel.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        tv_fab3viewmodel.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        fab_mainviewmdoel.setImageResource(R.drawable.ic_action_close)
    }
    fun viewmodels(){
        fab_mainviewmdoel.visibility = View.VISIBLE
        fab_download.visibility = View.GONE
        fab_edit.visibility = View.GONE
        fab_viewmodels.visibility = View.GONE
        fab_mainmenu.visibility = View.GONE
        fab_planviews.visibility = View.VISIBLE
        fab_editviewmodel.visibility = View.VISIBLE
        fab_downloadviemodel.visibility = View.VISIBLE
        nav_viewmodel.visibility = View.VISIBLE
        bottomnavigationplanview.visibility = View.GONE
        val fragment1 = this.childFragmentManager.findFragmentById(R.id.fragmentviewmodel) as NavHostFragment
        val fragment2 = this.childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        fragment1.view?.visibility = View.VISIBLE
        fragment2.view?.visibility = View.GONE

    }

    fun setUpBottomNavigation(){
        val fragment = this.childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = fragment.findNavController()
        bottomnavigationplanview.setupWithNavController(navController)

        val fragment2 = this.childFragmentManager.findFragmentById(R.id.fragmentviewmodel) as NavHostFragment
        val navController2 = fragment2.findNavController()
        nav_viewmodel.setupWithNavController(navController2)
    }

    fun textcolor(isavailable : Boolean, tv : TextView, list : List<EditText>, cd : CardView){
        var defaultcolor = if (isavailable) Constants.textcolor else ContextCompat.getColor(requireContext(),R.color.black)
        val colorpicker = AmbilWarnaDialog(requireContext(),defaultcolor,object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                defaultcolor = color

                if (list.isEmpty()){
                    Constants.bordercolor1 = color
                }else if(list.size == 1 ){
                    Constants.textcolor2 = color
                }
                else{
                    Constants.textcolor1 = color
                }
                Constants.bordercolor = color
                for (tv in list){
                    tv.setTextColor(defaultcolor)
                }
                tv.setTextColor(defaultcolor)
                cd.setCardBackgroundColor(defaultcolor)
            }

        })
        colorpicker.show()
    }


    fun detailsEditDialog(isavailable: Boolean,isavailable2: Boolean,isavailable3: Boolean){
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val customview = layoutInflater.inflate(R.layout.dialog_contactedit,null,false)
        alertDialog.setView(customview)
        val cd1 = customview.findViewById<CardView>(R.id.cd_textcolor)
        val cd2 = customview.findViewById<CardView>(R.id.cd_bordercolor)
        val cd3 = customview.findViewById<CardView>(R.id.cd_textcolor2)
        val tv1 = customview.findViewById<TextView>(R.id.tv_textcolor)
        val tv2 = customview.findViewById<TextView>(R.id.tv_bordercolor)
        val tv3 = customview.findViewById<TextView>(R.id.tv_textcolor2)
        val ed1 = customview.findViewById<EditText>(R.id.ed_name)
        val ed2 = customview.findViewById<EditText>(R.id.ed_cell)
        val ed3 = customview.findViewById<EditText>(R.id.ed_place)
        val ed4 = customview.findViewById<EditText>(R.id.ed_service)
        val submit = customview.findViewById<Button>(R.id.bt_submit)
        ed1.setText(Constants.customerDetails.name)
        ed2.setText(Constants.customerDetails.phone_number)
        ed3.setText(Constants.customerDetails.location)
        ed4.setText(Constants.customerDetails.services)
        if (isavailable){
            cd1.setCardBackgroundColor(Constants.textcolor)
            tv1.setTextColor(Constants.textcolor)
            ed1.setTextColor(Constants.textcolor)
            ed2.setTextColor(Constants.textcolor)
            ed3.setTextColor(Constants.textcolor)
        }
        if (isavailable3){
            cd3.setCardBackgroundColor(Constants.textcolor2)
            tv3.setTextColor(Constants.textcolor2)
            ed4.setTextColor(Constants.textcolor2)
        }
        if (isavailable2){
            cd2.setCardBackgroundColor(Constants.bordercolor)
            tv2.setTextColor(Constants.bordercolor)
        }
        cd1.setOnClickListener {
            textcolor(isavailable,tv1, listOf(ed1,ed2,ed3),cd1)
        }
        cd2.setOnClickListener {
            textcolor(isavailable2,tv2, listOf(),cd2)
        }
        cd3.setOnClickListener {
            textcolor(isavailable,tv3, listOf(ed4),cd3)
        }
        submit.setOnClickListener {
            Constants.customerDetails.name = ed1.text.toString()
            Constants.customerDetails.services = ed4.text.toString()
            Constants.customerDetails.phone_number = ed2.text.toString()
            Constants.customerDetails.location = ed3.text.toString()
            Constants.textcolor = Constants.textcolor1
            Constants.bordercolor = Constants.bordercolor1
            Constants.isDownload = true
            startActivity(Intent(requireContext(),HomeActivity::class.java))
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showFABMenu() {
        isFABOpen = true
        tv_fab1.visibility = View.VISIBLE
        tv_fab2.visibility = View.VISIBLE
        tv_fab3.visibility = View.VISIBLE
        fab_viewmodels.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        tv_fab1.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        fab_edit.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        tv_fab2.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        fab_download.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        tv_fab3.animate().translationY(-resources.getDimension(R.dimen.standard_155))
        fab_mainmenu.setImageResource(R.drawable.ic_action_close)
    }
    private fun closeFABMenu() {
        isFABOpen = false
        tv_fab1.visibility = View.GONE
        tv_fab2.visibility = View.GONE
        tv_fab3.visibility = View.GONE
        fab_viewmodels.animate().translationY(0F)
        tv_fab1.animate().translationY(0F)
        fab_edit.animate().translationY(0F)
        tv_fab2.animate().translationY(0F)
        fab_download.animate().translationY(0F)
        tv_fab3.animate().translationY(0F)
        fab_mainmenu.setImageResource(R.drawable.ic_action_edit)
    }
    private fun setCurrentFragment(fragment:Fragment)=
       requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView2,fragment)
            commit()
        }

}