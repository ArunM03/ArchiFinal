package com.codezcook.archiplanner.ui.dashboard.quotation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.PdfObject
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.fragment_elevation.*
import kotlinx.android.synthetic.main.fragment_fifthpagequo.*
import kotlinx.android.synthetic.main.fragment_firstpagequo.*
import kotlinx.android.synthetic.main.fragment_getdetails.*
import kotlinx.android.synthetic.main.fragment_getdetails.ed_4wallratio
import kotlinx.android.synthetic.main.fragment_getdetails.ed_bedroomdoor
import kotlinx.android.synthetic.main.fragment_getdetails.ed_bedroomdoorfit
import kotlinx.android.synthetic.main.fragment_getdetails.ed_bedroomdoorframe
import kotlinx.android.synthetic.main.fragment_getdetails.ed_ceiling
import kotlinx.android.synthetic.main.fragment_getdetails.ed_costofexteriorpaint
import kotlinx.android.synthetic.main.fragment_getdetails.ed_costofinteriorpaint
import kotlinx.android.synthetic.main.fragment_getdetails.ed_dimension
import kotlinx.android.synthetic.main.fragment_getdetails.ed_exteriorpaint
import kotlinx.android.synthetic.main.fragment_getdetails.ed_interior
import kotlinx.android.synthetic.main.fragment_getdetails.ed_loftbedroom
import kotlinx.android.synthetic.main.fragment_getdetails.ed_maindoor
import kotlinx.android.synthetic.main.fragment_getdetails.ed_maindoorhandle
import kotlinx.android.synthetic.main.fragment_getdetails.ed_maindoorlock
import kotlinx.android.synthetic.main.fragment_getdetails.ed_plasterinworkratio
import kotlinx.android.synthetic.main.fragment_getdetails.ed_slabcc
import kotlinx.android.synthetic.main.fragment_getdetails.ed_slabdiaofrod
import kotlinx.android.synthetic.main.fragment_getdetails.ed_staircasehandrailing
import kotlinx.android.synthetic.main.fragment_getdetails.ed_thicknessslab
import kotlinx.android.synthetic.main.fragment_getdetails.ed_typeofbrick
import kotlinx.android.synthetic.main.fragment_getdetails.ed_typeofslab
import kotlinx.android.synthetic.main.fragment_getdetails.ed_typeofstaircase
import kotlinx.android.synthetic.main.fragment_getdetails.ed_varnish
import kotlinx.android.synthetic.main.fragment_getdetails.ed_wallratio
import kotlinx.android.synthetic.main.fragment_getdetails.ed_windheadandframe
import kotlinx.android.synthetic.main.fragment_getdetails.ed_window
import kotlinx.android.synthetic.main.fragment_getdetails.tv_4wallratio
import kotlinx.android.synthetic.main.fragment_getdetails.tv_bedroomdoor
import kotlinx.android.synthetic.main.fragment_getdetails.tv_bedroomdoorfit
import kotlinx.android.synthetic.main.fragment_getdetails.tv_bedroomdoorframe
import kotlinx.android.synthetic.main.fragment_getdetails.tv_brickwork
import kotlinx.android.synthetic.main.fragment_getdetails.tv_ceiling
import kotlinx.android.synthetic.main.fragment_getdetails.tv_costofexteriorpaint
import kotlinx.android.synthetic.main.fragment_getdetails.tv_costofinteriorpaint
import kotlinx.android.synthetic.main.fragment_getdetails.tv_dimension
import kotlinx.android.synthetic.main.fragment_getdetails.tv_electricalwork
import kotlinx.android.synthetic.main.fragment_getdetails.tv_excavationwork
import kotlinx.android.synthetic.main.fragment_getdetails.tv_exteriorpaint
import kotlinx.android.synthetic.main.fragment_getdetails.tv_interior
import kotlinx.android.synthetic.main.fragment_getdetails.tv_loft
import kotlinx.android.synthetic.main.fragment_getdetails.tv_loftbedroom
import kotlinx.android.synthetic.main.fragment_getdetails.tv_maindoor
import kotlinx.android.synthetic.main.fragment_getdetails.tv_maindoorhandle
import kotlinx.android.synthetic.main.fragment_getdetails.tv_maindoorlock
import kotlinx.android.synthetic.main.fragment_getdetails.tv_paintingwork
import kotlinx.android.synthetic.main.fragment_getdetails.tv_plasteringwork
import kotlinx.android.synthetic.main.fragment_getdetails.tv_plasterinworkratio
import kotlinx.android.synthetic.main.fragment_getdetails.tv_roofslab
import kotlinx.android.synthetic.main.fragment_getdetails.tv_slabcc
import kotlinx.android.synthetic.main.fragment_getdetails.tv_slabdiaofrod
import kotlinx.android.synthetic.main.fragment_getdetails.tv_staircase
import kotlinx.android.synthetic.main.fragment_getdetails.tv_staircasehandrailing
import kotlinx.android.synthetic.main.fragment_getdetails.tv_switchesprce
import kotlinx.android.synthetic.main.fragment_getdetails.tv_thicknessslab
import kotlinx.android.synthetic.main.fragment_getdetails.tv_typeofbrick
import kotlinx.android.synthetic.main.fragment_getdetails.tv_typeofslab
import kotlinx.android.synthetic.main.fragment_getdetails.tv_typeofstaircase
import kotlinx.android.synthetic.main.fragment_getdetails.tv_varnish
import kotlinx.android.synthetic.main.fragment_getdetails.tv_wallratio
import kotlinx.android.synthetic.main.fragment_getdetails.tv_windheadandframe
import kotlinx.android.synthetic.main.fragment_getdetails.tv_window
import kotlinx.android.synthetic.main.fragment_getdetails.tv_windowgrill
import kotlinx.android.synthetic.main.fragment_getdetails.tv_woodwork
import kotlinx.android.synthetic.main.fragment_sizthpagequo.*
import kotlinx.android.synthetic.main.fragment_thirdpagequo.*
import android.widget.Toast

import com.codezcook.archiplanner.MainActivity

import android.os.Environment

import android.R
import android.graphics.Paint

import androidx.core.content.ContextCompat

import android.graphics.Typeface

import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import kotlinx.android.synthetic.main.fragment_fourpagequo.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class GetDetailsFragment : Fragment(com.codezcook.archiplanner2.R.layout.fragment_getdetails) {
    private var mInterstitialAd: InterstitialAd? = null
    var pageHeight = 1120
    var pagewidth = 792
    var bmp: Bitmap? = null
    var scaledbmp = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBannerAd()

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context,getString(com.codezcook.archiplanner2.R.string.quotation_interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        val activity = activity as HomeActivity
       val favt = activity.findViewById<ImageButton>(com.codezcook.archiplanner2.R.id.ib_allfavts)
        favt.visibility = View.INVISIBLE


        ed_companyname.setText(Constants.contactDetails.name)
        ed_place.setText(Constants.contactDetails.location)
        ed_phone.setText(Constants.contactDetails.phone_number)
        ed_notesdata.setText("This price does not include compound wall,septictank,bathroom fitting like shower,wallmixer,rainwater harvesting& safety gate")

        bt_submitdetails.setOnClickListener {
            if (!Constants.isSubscribed) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(getActivity())

                    setDetails()
                    Constants.viewlist = mutableListOf<View>()
                    startActivity(Intent(requireContext(), QuoPdfActivity::class.java))

                }
            }else {
                setDetails()
                Constants.viewlist = mutableListOf<View>()
                startActivity(Intent(requireContext(), QuoPdfActivity::class.java))
            }
        }
    }

    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_quotation.loadAd(adRequest)
        } else {
            banner_quotation.visibility = View.GONE
        }
    }

    fun setDetails(){
        val c = PdfObject
        c.companyname = ed_companyname.text.toString()
        c.cellno = ed_phone.text.toString()
        c.place = ed_place.text.toString()
        c.excavationwork = tv_excavationwork.text.toString()
        c.dimension = tv_dimension.text.toString()
        c.dimensiondata = ed_dimension.text.toString()
        c.pcc = tv_pcc.text.toString()
        c.thickeness = tv_thickness.text.toString()
        c.thickenessdata = ed_thickness.text.toString()
        c.ratio = tv_ratio.text.toString()
        c.ratiodata = ed_ratio.text.toString()
        c.flooring = tv_flooring.text.toString()
        c.flooringdata = ed_flooring.text.toString()
        c.footing = tv_footing.text.toString()
        c.concreteratio = tv_concreteratio.text.toString()
        c.concreteratiodata = ed_concreteratio.text.toString()
        c.concretethickness = tv_concretethickness.text.toString()
        c.concretethicknessdata = ed_concretethickness.text.toString()
        c.diaofrod = tv_diaofrod.text.toString()
        c.diaofroddata = ed_diaofrod.text.toString()
        c.cc = tv_cc.text.toString()
        c.ccdata = ed_cc.text.toString()
        c.developementlength = tv_developmentlength.text.toString()
        c.developementlengthdata = ed_developmentlength.text.toString()
        c.column = tv_column.text.toString()
        c.columndimension = tv_columndimension.text.toString()
        c.columndimensiondata = ed_columndimension.text.toString()
        c.columndiaofrod = tv_columndiaofrod.text.toString()
        c.columndiaofroddata = ed_columndiaofrod.text.toString()
        c.rings = tv_rings.text.toString()
        c.ringdata = ed_rings.text.toString()
        c.columncc = tv_columncc.text.toString()
        c.columnccdata = ed_columncc.text.toString()
        c.columndevelopmenlength = tv_columndevelopmentlength.text.toString()
        c.columndevelopmenlengthdata = ed_columndevelopmentlength.text.toString()
        c.columnconcreteratio = tv_concretethickness.text.toString()
        c.columnconcreteratiodata = ed_columnconcreteratio.text.toString()
        c.plinth = tv_plinthbeam.text.toString()
        c.plinthdimension = tv_plinthdimension.text.toString()
        c.plinthdimensiondata = ed_plinthdimension.text.toString()
        c.plinthdiaofrod = tv_plinthdiaofrod.text.toString()
        c.plinthdiaofroddata = ed_plinthdiaofrod.text.toString()
        c.plinthrings = tv_plinthrings.text.toString()
        c.plinthringsdata = ed_plinthrings.text.toString()
        c.plinthcc = tv_plinthncc.text.toString()
        c.plinthccdata = ed_plinthncc.text.toString()
        c.plinthdevelopmentlength = tv_plinthdevelopmentlength.text.toString()
        c.plinthdevelopmentlengthdata = ed_plinthdevelopmentlength.text.toString()
        c.plinthconcreteratio = tv_plinthconcreteratio.text.toString()
        c.plinthconcreteratiodata = ed_plinthconcreteratio.text.toString()
        c.lintel = tv_lintelbeam.text.toString()
        c.linteldimension = tv_linteldimension.text.toString()
        c.linteldimensiondata= ed_linteldimension.text.toString()
        c.linteldiaofrod= tv_linteldiaofrod.text.toString()
        c.linteldiaofroddata = ed_linteldiaofrod.text.toString()
        c.lintelrings = tv_lintelrings.text.toString()
        c.lintelringsdata = ed_lintelrings.text.toString()
        c.lintelcc = tv_lintelncc.text.toString()
        c.lintelccdata = ed_lintelncc.text.toString()
        c.linteldevelopmentlength = tv_linteldevelopmentlength.text.toString()
        c.linteldevelopmentlengthdata = ed_linteldevelopmentlength.text.toString()
        c.lintelconcreteratio = tv_lintelconcreteratio.text.toString()
        c.lintelconcreteratiodata = ed_lintelconcreteratio.text.toString()
        c.basement = tv_basement.text.toString()
        c.height = tv_height.text.toString()
        c.heightdata = ed_height.text.toString()
        c.fillingmaterial = tv_fillingmaterial.text.toString()
        c.fillingmaterialdata = ed_fillingmaterial.text.toString()
        c.roofslab = tv_roofslab.text.toString()
        c.typeofslab = tv_typeofslab.text.toString()
        c.typeofslabdata = ed_typeofslab.text.toString()
        c.diaofrodslab = tv_slabdiaofrod.text.toString()
        c.diaofrodslabdata = ed_slabdiaofrod.text.toString()
        c.ccslab = tv_slabcc.text.toString()
        c.ccslabdata = ed_slabcc.text.toString()
        c.thicknessofslab = tv_thicknessslab.text.toString()
        c.thicknessofslabdata = ed_thicknessslab.text.toString()
        c.brickwork = tv_brickwork.text.toString()
        c.typeofbrick = tv_typeofbrick.text.toString()
        c.typeofbrickdata = ed_typeofbrick.text.toString()
        c.wall9 = tv_wallratio.text.toString()
        c.wall9data = ed_wallratio.text.toString()
        c.wall45 = tv_4wallratio.text.toString()
        c.wall45data = ed_4wallratio.text.toString()
        c.plasteringwork = tv_plasteringwork.text.toString()
        c.plasteringratio = tv_plasterinworkratio.text.toString()
        c.plasteringratiodata = ed_plasterinworkratio.text.toString()
        c.tileswork = tv_tileswork.text.toString()
        c.hall = tv_hall.text.toString()
        c.halldata = ed_hall.text.toString()
        c.bathroom = tv_bathroom.text.toString()
        c.bathroomdata = ed_bathroom.text.toString()
        c.carparking = tv_carparking.text.toString()
        c.carparkingdata = ed_carparking.text.toString()
        c.kitchen = tv_kitchenwall.text.toString()
        c.kitchendata = ed_kitchenwall.text.toString()
        c.bathroomwall = tv_bathroomwall.text.toString()
        c.bathroomwalldata = ed_bathroomwall.text.toString()
        c.skirtingheight = tv_skirting.text.toString()
        c.skirtingheightdata = ed_skirting.text.toString()
        c.kitchenslap = tv_kitchenslab.text.toString()
        c.kitchenslapdata = ed_kitchenslab.text.toString()
        c.cement = tv_cementbrand.text.toString()
        c.cementbrand = tv_cementbrandname.text.toString()
        c.cementbranddata = ed_cementbrandname.text.toString()
        c.sandhead =  tv_sand.text.toString()
        c.sand = tv_sandname.text.toString()
        c.sanddata = ed_sandname.text.toString()
        c.electricwork = tv_electricalwork.text.toString()
        c.switchprice = tv_switchesprce.text.toString()
        c.switchpricedata = ed_switchesprce.text.toString()
        c.wirebrand = tv_wiresbrand.text.toString()
        c.wirebranddata = ed_wiresbrand.text.toString()
        c.fanpoint = tv_fanpoint.text.toString()
        c.fanpointdata = ed_fanpoint.text.toString()
        c.lightpoint = tv_lightpoint.text.toString()
        c.lightpointdata = ed_lightpoint.text.toString()
        c.acpoint = tv_acpoint.text.toString()
        c.acpointdata = ed_acpoint.text.toString()
        c.heaterpoint = tv_headerpoint.text.toString()
        c.heaterpointdata = ed_headerpoint.text.toString()
        c.lightfittling = tv_lightfitting.text.toString()
        c.lightfittlingdata = ed_lightfitting.text.toString()
        c.switchbox = tv_switchbox.text.toString()
        c.switchboxdata = ed_switchbox.text.toString()
        c.plumbing = tv_plumbing.text.toString()
        c.plumpingconcealedpipe = tv_concealedpipe.text.toString()
        c.plumpingconcealedpipedata = ed_concealedpipe.text.toString()
        c.outerpipe = tv_outerpipe.text.toString()
        c.outerpipedata = ed_outerpipe.text.toString()
        c.watertactap = tv_watertactap.text.toString()
        c.watertactapdata = ed_watertactap.text.toString()
        c.watertank = tv_watertank.text.toString()
        c.watertankdata = ed_watertank.text.toString()
        c.indianwesterncloset = tv_indianwestercloset.text.toString()
        c.indianwesternclosetdata = ed_indianwestercloset.text.toString()
        c.paintingwork = tv_paintingwork.text.toString()
        c.ceiling = tv_ceiling.text.toString()
        c.ceilingdata = ed_ceiling.text.toString()
        c.interiorpainting = tv_interior.text.toString()
        c.interiorpaintingdata = ed_interior.text.toString()
        c.exteriorpainting = tv_exteriorpaint.text.toString()
        c.exteriorpaintingdata = ed_exteriorpaint.text.toString()
        c.varnish = tv_varnish.text.toString()
        c.varnishdata = ed_varnish.text.toString()
        c.window = tv_window.text.toString()
        c.windowdata = ed_window.text.toString()
        c.costofinterior = tv_costofinteriorpaint.text.toString()
        c.costofinteriordata = ed_costofinteriorpaint.text.toString()
        c.costofexterior = tv_costofexteriorpaint.text.toString()
        c.costofexteriordata = ed_costofexteriorpaint.text.toString()
        c.woodwork = tv_woodwork.text.toString()
        c.maindoor = tv_maindoor.text.toString()
        c.maindoordata = ed_maindoor.text.toString()
        c.maindoorlock = tv_maindoorlock.text.toString()
        c.maindoorlockdata = ed_maindoorlock.text.toString()
        c.maindoorhandle = tv_maindoorhandle.text.toString()
        c.maindoorhandledata = ed_maindoorhandle.text.toString()
        c.bedroomdoorframe = tv_bedroomdoorframe.text.toString()
        c.bedroomdoorframedata = ed_bedroomdoorframe.text.toString()
        c.bedroomdoor = tv_bedroomdoor.text.toString()
        c.bedroomdoordata = ed_bedroomdoor.text.toString()
        c.bedroomdoorfitting = tv_bedroomdoorfit.text.toString()
        c.bedroomdoorfittingdata = ed_bedroomdoorfit.text.toString()
        c.windowheadandframe = tv_windheadandframe.text.toString()
        c.windowheadandframedata = ed_windheadandframe.text.toString()
        c.windowgrill = tv_windowgrill.text.toString()
        c.windowgrilldata = ed_windowgrill.text.toString()
        c.bathroomdoorandframe = tv_bathroomdoor.text.toString()
        c.bathroomdoorandframedata = ed_bathroomdoor.text.toString()
        c.bathroomventilator = tv_bathroomventilator.text.toString()
        c.bathroomventilatordata = ed_bathroomventilator.text.toString()
        c.windowandventilator = tv_windowventil.text.toString()
        c.windowandventilatordata = ed_windowventil.text.toString()
        c.staircase = tv_staircase.text.toString()
        c.typeofstaircase = tv_typeofstaircase.text.toString()
        c.typeofstaircasedata = ed_typeofstaircase.text.toString()
        c.staircasehandriding = tv_staircasehandrailing.text.toString()
        c.staircasehandridingdata = ed_staircasehandrailing.text.toString()
        c.loft = tv_loft.text.toString()
        c.bedroom = tv_loftbedroom.text.toString()
        c.bedroomdata = ed_loftbedroom.text.toString()
        c.loftkitchen = tv_loftkitchen.text.toString()
        c.loftkitchendata = ed_loftkitchen.text.toString()
        c.shelf = tv_shelf.text.toString()
        c.shelfdata = ed_shelfdata.text.toString()
        c.steelbrand = tv_steelbrand.text.toString()
        c.steelbranddata = ed_steelbranddata.text.toString()
        c.ratepersqdata = ed_ratepersqfeetdata.text.toString()
        c.notes = ed_notesdata.text.toString()
    }

}