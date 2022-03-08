package com.codezcook.archiplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.PdfObject
import com.codezcook.archiplanner.data.*
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.fragment_fifthpagequo.view.*
import kotlinx.android.synthetic.main.fragment_fifthpagequo.view.tv_cellno5
import kotlinx.android.synthetic.main.fragment_fifthpagequo.view.tv_companyname5
import kotlinx.android.synthetic.main.fragment_fifthpagequo.view.tv_place5
import kotlinx.android.synthetic.main.fragment_fifthpagequo_2.view.*
import kotlinx.android.synthetic.main.fragment_firstpagequo.view.*
import kotlinx.android.synthetic.main.fragment_firstpagequo.view.ed_dimension
import kotlinx.android.synthetic.main.fragment_firstpagequo.view.tv_companyname
import kotlinx.android.synthetic.main.fragment_firstpagequo.view.tv_dimension
import kotlinx.android.synthetic.main.fragment_firstpagequo.view.tv_excavationwork
import kotlinx.android.synthetic.main.fragment_firstpagequo.view.tv_place
import kotlinx.android.synthetic.main.fragment_fourpagequo.view.*
import kotlinx.android.synthetic.main.fragment_fourpagequo_2.view.*
import kotlinx.android.synthetic.main.fragment_getdetails.*
import kotlinx.android.synthetic.main.fragment_getdetails.view.*
import kotlinx.android.synthetic.main.fragment_secondpagequo.view.*
import kotlinx.android.synthetic.main.fragment_secondpagequo_2.view.*
import kotlinx.android.synthetic.main.fragment_sizthpagequo.view.*
import kotlinx.android.synthetic.main.fragment_sizthpagequo_2.view.*
import kotlinx.android.synthetic.main.fragment_thirdpagequo.view.*
import kotlinx.android.synthetic.main.fragment_thirdpagequo_2.view.*
import kotlinx.android.synthetic.main.rv_plans.view.*
import kotlinx.android.synthetic.main.rv_topic.view.*


class VpAdapter : RecyclerView.Adapter<VpAdapter.PlaylistViewHolder>() {


    inner class PlaylistViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    private val diffCallback = object : DiffUtil.ItemCallback<Int>(){

        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var topicList: List<Int>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((Int) -> Unit)? = null


    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
      return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layout1 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_firstpagequo,parent,false))
        val layout2 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_secondpagequo,parent,false))
        val layout21 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_secondpagequo_2,parent,false))
        val layout3 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_thirdpagequo,parent,false))
        val layout31 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_thirdpagequo_2,parent,false))
        val layout4 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_fourpagequo,parent,false))
        val layout41 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_fourpagequo_2,parent,false))

        val layout5 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_fifthpagequo,parent,false))
        val layout52 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_fifthpagequo_2,parent,false))
        val layout6 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_sizthpagequo,parent,false))

        val layout61 = PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_sizthpagequo_2,parent,false))
        return  when(viewType){
            0 -> layout1
            1 -> layout2
            2 -> layout21
            3 -> layout3
            4 -> layout31
            5 -> layout4
            6 -> layout41
            7 -> layout5
            8 -> layout52
            9 -> layout6
            10-> layout61
            else -> layout61
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = topicList[position]
        val c = PdfObject
        when(holder.itemViewType) {
            0 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname.text = c.companyname
                    tv_cellno.text = c.cellno
                    tv_place.text = c.place
                    tv_excavationwork.text = c.excavationwork
                    tv_dimension.setText(c.dimension)
                    ed_dimension.setText(c.dimensiondata)
                    tv_pcc2.setText(c.pcc)
                    tv_thickness2.setText(c.thickeness)
                    ed_thickness2.setText(c.thickenessdata)
                    tv_ratio2.setText(c.ratio)
                    ed_ratio2.setText(c.ratiodata)
                    tv_flooring2.setText(c.flooring)
                    ed_flooring2.setText(c.flooringdata)
                    tv_footing2.setText(c.footing)
                    tv_concreteratio2.setText(c.concreteratio)
                    ed_concreteratio2.setText(c.concreteratiodata)
                    tv_concretethickness2.setText(c.concretethickness)
                    ed_concretethickness2.setText(c.concretethicknessdata)
                    tv_diaofrod2.setText(c.diaofrod)
                    ed_diaofrod2.setText(c.diaofroddata)

                }
            }
            1 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname2.text = c.companyname
                    tv_cellno2.text = c.cellno
                    tv_place2.text = c.place
                    tv_cc2.setText(c.cc)
                    ed_cc2.setText(c.ccdata)
                    tv_developmentlength2.setText(c.developementlength)
                    ed_developmentlength2.setText(c.developementlengthdata)
                    tv_column2.setText(c.column)
                    tv_columndimension2.setText(c.columndimension)
                    ed_columndimension2.setText(c.columndimensiondata)
                    tv_columndiaofrod2.setText(c.columndiaofrod)
                    ed_columndiaofrod2.setText(c.columndiaofroddata)
                    tv_rings2.setText(c.rings)
                    ed_rings2.setText(c.ringdata)
                    tv_columncc2.setText(c.columncc)
                    ed_columncc2.setText(c.columnccdata)
                    tv_columndevelopmentlength2.setText(c.columndevelopmenlength)
                    ed_columndevelopmentlength2.setText(c.columndevelopmenlengthdata)
                    tv_columnconcreteratio2.setText(c.columnconcreteratio)
                    ed_columnconcreteratio2.setText(c.columnconcreteratiodata)

                }
            }
            2 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname21.text = c.companyname
                    tv_cellno21.text = c.cellno
                    tv_place21.text = c.place



                    tv_plinthbeam2.setText(c.plinth)
                    tv_plinthdimension2.setText(c.plinthdimension)
                    ed_plinthdimension2.setText(c.plinthdimensiondata)
                    tv_plinthdiaofrod2.setText(c.plinthdiaofrod)
                    ed_plinthdiaofrod2.setText(c.plinthdiaofroddata)
                    tv_plinthrings2.setText(c.plinthrings)
                    ed_plinthrings2.setText(c.plinthringsdata)
                    tv_plinthncc2.setText(c.plinthcc)
                    ed_plinthncc2.setText(c.plinthccdata)
                    tv_plinthdevelopmentlength2.setText(c.plinthdevelopmentlength)
                    ed_plinthdevelopmentlength2.setText(c.plinthdevelopmentlengthdata)
                    tv_plinthconcreteratio2.setText(c.plinthconcreteratio)
                    ed_plinthconcreteratio2.setText(c.plinthconcreteratiodata)
                    tv_lintelbeam2.setText(c.lintel)
                    tv_linteldimension2.setText(c.linteldimension)
                    ed_linteldimension2.setText(c.linteldimensiondata)
                    tv_linteldiaofrod2.setText(c.linteldiaofrod)
                    ed_linteldiaofrod2.setText(c.linteldiaofroddata)

                }
            }
            3 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname3.text = c.companyname
                    tv_cellno3.text = c.cellno
                    tv_place3.text = c.place

                    tv_lintelrings2.setText(c.lintelrings)
                    ed_lintelrings2.setText(c.lintelringsdata)
                    tv_lintelncc2.setText(c.lintelcc)
                    ed_lintelncc2.setText(c.lintelccdata)
                    tv_linteldevelopmentlength2.setText(c.linteldevelopmentlength)
                    ed_linteldevelopmentlength2.setText(c.linteldevelopmentlengthdata)
                    tv_lintelconcreteratio2.setText(c.lintelconcreteratio)
                    ed_lintelconcreteratio2.setText(c.lintelconcreteratiodata)
                    tv_basement2.setText(c.basement)
                    tv_height2.setText(c.height)
                    ed_height2.setText(c.heightdata)
                    tv_fillingmaterial2.setText(c.fillingmaterial)
                    ed_fillingmaterial2.setText(c.fillingmaterialdata)
                    tv_roofslab2.setText(c.roofslab)
                    tv_typeofslab2.setText(c.typeofslab)
                    ed_typeofslab2.setText(c.typeofslabdata)
                    tv_slabdiaofrod2.setText(c.diaofrodslab)
                    ed_slabdiaofrod2.setText(c.diaofrodslabdata)

                }
            }
            4 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname31.text = c.companyname
                    tv_cellno31.text = c.cellno
                    tv_place31.text = c.place
                    tv_slabcc2.setText(c.ccslab)
                    ed_slabcc2.setText(c.ccslabdata)
                    tv_thicknessslab2.setText(c.thicknessofslab)
                    ed_thicknessslab2.setText(c.thicknessofslabdata)
                    tv_brickwork2.setText(c.brickwork)
                    tv_typeofbrick2.setText(c.typeofbrick)
                    ed_typeofbrick2.setText(c.typeofbrickdata)
                    tv_wallratio2.setText(c.wall9)
                    ed_wallratio2.setText(c.wall9data)
                    tv_4wallratio2.setText(c.wall45)
                    ed_4wallratio2.setText(c.wall45data)
                    tv_plasteringwork2.setText(c.plasteringwork)
                    tv_plasterinworkratio2.setText(c.plasteringratio)
                    ed_plasterinworkratio2.setText(c.plasteringratiodata)


                    tv_tileswork2.setText(c.tileswork)
                    tv_hall2.setText(c.hall)
                    ed_hall2.setText(c.halldata)
                    tv_bathroom2.setText(c.bathroom)
                    ed_bathroom2.setText(c.bathroomdata)


                }
            }
            5 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname4.text = c.companyname
                    tv_cellno4.text = c.cellno
                    tv_place4.text = c.place

                    tv_carparking2.setText(c.carparking)
                    ed_carparking2.setText(c.carparkingdata)
                    tv_kitchenwall2.setText(c.kitchen)
                    ed_kitchenwall2.setText(c.kitchendata)
                    tv_bathroomwall2.setText(c.bathroomwall)
                    ed_bathroomwall2.setText(c.bathroomwalldata)
                    tv_skirting2.setText(c.skirtingheight)
                    ed_skirting2.setText(c.skirtingheightdata)
                    tv_kitchenslab2.setText(c.kitchenslap)
                    ed_kitchenslab2.setText(c.kitchenslapdata)
                    tv_cementbrand2.setText(c.cement)
                    tv_cementbrandname2.setText(c.cementbrand)
                    ed_cementbrandname2.setText(c.cementbranddata)

                    tv_sand2.setText(c.sandhead)
                    tv_sandname2.setText(c.sand)
                    ed_sandname2.setText(c.sanddata)


                }
            }
            6 -> {
                holder.itemView.apply {
                    tv_companyname41.text = c.companyname
                    tv_cellno41.text = c.cellno
                    tv_place41.text = c.place

                    tv_electricalwork2.setText(c.electricwork)
                    tv_switchesprce2.setText(c.switchprice)
                    ed_switchesprce2.setText(c.switchpricedata)
                    tv_wiresbrand2.setText(c.wirebrand)
                    ed_wiresbrand2.setText(c.wirebranddata)
                    tv_fanpoint2.setText(c.fanpoint)
                    ed_fanpoint2.setText(c.fanpointdata)
                    tv_lightpoint2.setText(c.lightpoint)
                    ed_lightpoint2.setText(c.lightpointdata)
                    tv_acpoint2.setText(c.acpoint)
                    ed_acpoint2.setText(c.acpointdata)
                    tv_headerpoint2.setText(c.heaterpoint)
                    ed_headerpoint2.setText(c.heaterpointdata)
                    tv_lightfitting2.setText(c.lightfittling)
                    ed_lightfitting2.setText(c.lightfittlingdata)
                    tv_switchbox2.setText(c.switchbox)
                    ed_switchbox2.setText(c.switchboxdata)

                }
        }
            7 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname5.text = c.companyname
                    tv_cellno5.text = c.cellno
                    tv_place5.text = c.place

            tv_plumbing2.setText(c.plumbing)
                    tv_concealedpipe2.setText(c.plumpingconcealedpipe)
                    ed_concealedpipe2.setText(c.plumpingconcealedpipedata)
                    tv_outerpipe2.setText(c.outerpipe)
                    ed_outerpipe2.setText(c.outerpipedata)
                    tv_watertactap2.setText(c.watertactap)
                    ed_watertactap2.setText(c.watertactapdata)
                    tv_watertank2.setText(c.watertank)
                    ed_watertank2.setText(c.watertankdata)
                    tv_indianwestercloset2.setText(c.indianwesterncloset)
                    ed_indianwestercloset2.setText(c.indianwesternclosetdata)
                    tv_paintingwork2.setText(c.paintingwork)
                    tv_ceiling2.setText(c.ceiling)
                    ed_ceiling2.setText(c.ceilingdata)
            tv_exteriorpaint2.setText(c.exteriorpainting)
                    ed_exteriorpaint2.setText(c.exteriorpaintingdata)

                    tv_interior2.setText(c.interiorpainting)
                    ed_interior2.setText(c.interiorpaintingdata)

                }
            }
            8 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname51.text = c.companyname
                    tv_cellno51.text = c.cellno
                    tv_place51.text = c.place
                                tv_varnish2.setText(c.varnish)
                    ed_varnish2.setText(c.varnishdata)
                    tv_window2.setText(c.window)
                    ed_window2.setText(c.windowdata)
                    tv_costofinteriorpaint2.setText(c.costofinterior)
                    ed_costofinteriorpaint2.setText(c.costofinteriordata)
                    tv_costofexteriorpaint2.setText(c.costofexterior)
                    ed_costofexteriorpaint2.setText(c.costofexteriordata)
            tv_woodwork2.setText(c.woodwork)
                    tv_maindoor2.setText(c.maindoor)
                    ed_maindoor2.setText(c.maindoordata)
                    tv_maindoorlock2.setText(c.maindoorlock)
                    ed_maindoorlock2.setText(c.maindoorlockdata)
                    tv_maindoorhandle2.setText(c.maindoorhandle)
                    ed_maindoorhandle2.setText(c.maindoorhandledata)
                    tv_bedroomdoorframe2.setText(c.bedroomdoorframe)
                    ed_bedroomdoorframe2.setText(c.bedroomdoorframedata)

                }
            }
            9 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname6.text = c.companyname
                    tv_cellno6.text = c.cellno
                    tv_place6.text = c.place
                                        tv_bedroomdoor2.setText(c.bedroomdoor)
                    ed_bedroomdoor2.setText(c.bedroomdoordata)
                    tv_bedroomdoorfit2.setText(c.bedroomdoorfitting)
                    ed_bedroomdoorfit2.setText(c.bedroomdoorfittingdata)
                    tv_windheadandframe2.setText(c.windowheadandframe)
                    ed_windheadandframe2.setText(c.windowheadandframedata)
                    tv_windowgrill2.setText(c.windowgrill)
                    ed_windowgrill2.setText(c.windowgrilldata)
                    tv_bathroomdoor2.setText(c.bathroomdoorandframe)
                    ed_bathroomdoor2.setText(c.bathroomdoorandframedata)
                    tv_bathroomventilator2.setText(c.bathroomventilator)
                    ed_bathroomventilator2.setText(c.bathroomventilatordata)
                    tv_windowventil2.setText(c.windowandventilator)
                    ed_windowventil2.setText(c.windowandventilatordata)
                    tv_staircase2.setText(c.staircase)
                    tv_typeofstaircase2.setText(c.typeofstaircase)
                    ed_typeofstaircase2.setText(c.typeofstaircasedata)
                }
            }
            10 -> {
                holder.itemView.apply {
                    Constants.viewlist.add(this)
                    tv_companyname61.text = c.companyname
                    tv_cellno61.text = c.cellno
                    tv_place61.text = c.place
                                        tv_staircasehandrailing2.setText(c.staircasehandriding)
                    ed_staircasehandrailing2.setText(c.staircasehandridingdata)
                    tv_loft2.setText(c.loft)
                    tv_loftbedroom2.setText(c.bedroom)
                    ed_loftbedroom2.setText(c.bedroomdata)
                    tv_loftkitchen2.setText(c.loftkitchen)
                    ed_loftkitchen2.setText(c.loftkitchendata)


                    tv_shelf2.setText(c.shelf)
                    ed_shelfdata2.setText(c.shelfdata)
                    tv_steelbranddata2.setText(c.steelbrand)
                    ed_steelbranddata2.setText(c.steelbranddata)
                    ed_ratepersqfeetdata2.setText(c.ratepersqdata)
                    ed_notesdata2.setText(c.notes)
                }
            }

        }

    }



}