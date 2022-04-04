package com.codezcook.archiplanner.ui.dashboard.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.MainActivity
import com.codezcook.archiplanner.adapter.PlanAdapter
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.data.PlanResponseItem
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_elevation.*
import kotlinx.android.synthetic.main.fragment_planlist.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PlanListFragment() : Fragment(R.layout.fragment_planlist) {

    lateinit var viewmodel : PlanViewmodel
    var filter = "all"
    val planAdapter = PlanAdapter()
    var curPos = 0
    var totalCountPerPage = 15
    var groupSize = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)



        viewmodel.paginationAdLive.observe(viewLifecycleOwner, Observer {
            Constants.paginationAd = it.images
            startActivity(Intent(requireContext(),MainActivity::class.java))
        })

        viewmodel.getPlans()

        setBannerAd()

        rv_plans.adapter = planAdapter
        rv_plans.layoutManager = LinearLayoutManager(requireContext())
        rv_plans.isNestedScrollingEnabled = false

        planAdapter.setOnItemClickListener {
            Constants.curPlanResponseItem = it
            val planlist = planAdapter.planlist
            Constants.curPlanPos = planlist.indexOf(it)
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_planListFragment_to_planVPFragment)
          //  nav_host_fragment_content_home.findNavController().navigate(R.id.action_planListFragment_to_planViewFragment)
       //startActivity(Intent(requireContext(),PlanViewActivity::class.java))
        }

        planAdapter.share {
            share(it.view, it.planResponseItem)
        }
        planAdapter.unSave {
            viewmodel.unSaveFavtPost(FirebaseAuth.getInstance().currentUser?.uid!!,it.id!!)
            planAdapter.notifyDataSetChanged()
        }

        planAdapter.save {
            viewmodel.saveFavtPost(FirebaseAuth.getInstance().currentUser?.uid!!,it.id!!)
            planAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(),"Saved Successfully",Toast.LENGTH_SHORT).show()
        }

        ed_search.onSubmit { submit(planAdapter) }

        viewmodel.contactLive.observe(viewLifecycleOwner, Observer { it ->
            if (!Constants.contactUpdated){
                Constants.contactDetails = it[0]
                Constants.customerDetails = it[0]
            }
        })

        ib_filter.setOnClickListener {
            filterByRoom(planAdapter)
        }
        viewmodel.errorPlanLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })

        viewmodel.errorSearch.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })

        viewmodel.errorSaveFavtPlan.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })

        viewmodel.errorunsavePlanLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })
        viewmodel.unSavePlanLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Successfully removed",Toast.LENGTH_SHORT).show()
        })

        getPlanList(planAdapter)

        viewmodel.errorPlanLive.observe(viewLifecycleOwner, Observer {
            progressbar_plans.visibility = View.GONE
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })
    }


    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_planlist.loadAd(adRequest)
        }else {
            banner_planlist.visibility = View.GONE
        }
    }
    fun <T> partition(list: List<T>, n: Int): Array<MutableList<T>?>
    {
        // get the size of the list
        val size = list.size

        // calculate the total number of partitions `m` of size `n` each
        var m = size / n
        if (size % n != 0) m++

        // create `m` empty lists and initialize them using `subList()`
        val partition: Array<MutableList<T>?> = arrayOfNulls(m)
        for (i in 0 until m)
        {
            val fromIndex = i * n
            val toIndex = if (i * n + n < size) i * n + n else size

            partition[i] = list.subList(fromIndex, toIndex).toMutableList()
        }

        // return the lists (The final list might have a fewer number of items)
        return partition
    }

    fun createGroupList(list : List<PlanResponseItem>){
        val newlist = partition(list,15)
        groupSize = newlist.size

        setNextAndPreviousVisibility()

        if (curPos < newlist.size) {
//            Toast.makeText(requireContext(),"Total : ${list.size} and newlist :  ${newlist.size} and curlist : ${newlist[curPos]?.size}",Toast.LENGTH_LONG).show()
            planAdapter.planlist =  newlist[curPos]!!
            planAdapter.notifyDataSetChanged()
        }

        bt_next.setOnClickListener {
            curPos++
            Constants.nextCount++
            if (Constants.nextCount == 9) {
                viewmodel.getPaginationAd()
                Constants.nextCount = 0
            }
            setNextAndPreviousVisibility()
            if (curPos < newlist.size) {
                planAdapter.planlist = newlist[curPos]!!
                planAdapter.notifyDataSetChanged()
            }
        }

       bt_previous.setOnClickListener {
            curPos--
            setNextAndPreviousVisibility()
            if (curPos < newlist.size) {
                planAdapter.planlist = newlist[curPos]!!
                planAdapter.notifyDataSetChanged()
            }
        }

       // Toast.makeText(requireContext(),"Total : ${list.size} and newlist :  ${newlist.size}",Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        planAdapter.notifyDataSetChanged()
        super.onResume()
    }

    fun setNextAndPreviousVisibility() {
        if (groupSize > curPos){
            bt_next.visibility = View.VISIBLE
        }else{
            bt_next.visibility = View.GONE
        }
        if ( curPos != 0) {
            bt_previous.visibility = View.VISIBLE
        } else {
            bt_previous.visibility = View.GONE
        }
    }

    private fun getPlanList(planAdapter: PlanAdapter,searchword : String = "" ) {
        viewmodel.planLive.observe(viewLifecycleOwner, Observer { it ->
            progressbar_plans.visibility = View.GONE
           val curlist  = it.filter { item -> item.facing == Constants.curPlan }
           val curlist2 = if (filter != "all") curlist.filter { item  -> item.room == filter } else curlist
            if (curlist.isEmpty()){
                tv_comingsoon.visibility = View.VISIBLE
            }else{
                tv_comingsoon.visibility = View.GONE
            }
            if (searchword.isNotEmpty()){
                val list = filterBySearchWord(searchword,curlist2)
                Constants.planlist = list
                createGroupList(list)
             //   planAdapter.planlist = list
            }else{
                Constants.planlist = curlist2
                createGroupList(curlist2)
               // planAdapter.planlist = curlist2
            }
            viewmodel.getContact()
            planAdapter.notifyDataSetChanged()
        })
    }
    fun submit(planAdapter: PlanAdapter){
        val searchword = ed_search.text.toString()
        getPlanList(planAdapter,searchword)
        viewmodel.sendSearchWord(searchword)
   }
    fun filterBySearchWord(search : String,list : List<PlanResponseItem>) : List<PlanResponseItem>{
        val newlist = mutableListOf<PlanResponseItem>()
        for (plan in list){
            if (plan.tags?.contains(search) == true){
                newlist.add(plan)
            }
        }
        return newlist
    }


    @SuppressLint("SetWorldReadable")
    private fun share(view: View,planResponseItem: PlanResponseItem) {
        val bitmap =  createViewBitmap(view)
        val randomString = UUID.randomUUID().toString().substring(0,15)
        try {
            val file = File(requireActivity().externalCacheDir, "pic_${randomString}.png")
            val outputStrem = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStrem)
            outputStrem.flush()
            outputStrem.close()
            file.setReadable(true, false)
            val imageuri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.homefolder.example.provider.archplanner",
                file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val text = "${planResponseItem.name} Hey, Please check this app" + " https://play.google.com/store/apps/details?id=" + requireActivity().packageName
            intent.putExtra(Intent.EXTRA_TEXT,text)
            intent.putExtra(Intent.EXTRA_STREAM,imageuri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "share by"))
        }catch (e: Exception){
            Toast.makeText(requireContext(),"${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    fun createViewBitmap(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawable = view.background
        if (drawable != null){
            drawable.draw(canvas)
        }else{
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }
    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                func()
            }

            true

        }
    }
    fun filterByRoom(planAdapter: PlanAdapter){
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val customview = layoutInflater.inflate(R.layout.dialog_filter,null,false)
        alertDialog.setView(customview)
        val rb1bhk = customview.findViewById<RadioButton>(R.id.rb_1bhk)
        val rb2bhk = customview.findViewById<RadioButton>(R.id.rb_2bhk)
        val rb3bhk = customview.findViewById<RadioButton>(R.id.rb_3bhk)
        val rb4bhk = customview.findViewById<RadioButton>(R.id.rb_4bhk)
        val rball = customview.findViewById<RadioButton>(R.id.rb_all)
        val rg = customview.findViewById<RadioGroup>(R.id.rg_room)
        when(filter){
            "1bhk" -> rb1bhk.isChecked = true
            "2bhk" -> rb2bhk.isChecked = true
            "3bhk" -> rb3bhk.isChecked = true
            "Above 4bhk" -> rb4bhk.isChecked = true
            else -> rball.isChecked = true
        }
        rg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_1bhk -> filter = "1bhk"
                R.id.rb_2bhk -> filter = "2bhk"
                R.id.rb_3bhk -> filter = "3bhk"
                R.id.rb_4bhk -> filter = "Above 4bhk"
                R.id.rb_all -> filter = "all"
            }
            getPlanList(planAdapter)
        }
        alertDialog.show()
    }
}

data class  PlanResponseItemGroup(
    val list : List<PlanResponseItem>
)