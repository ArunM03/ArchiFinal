package com.codezcook.archiplanner.ui.dashboard.qa

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.adapter.QAAdapter
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.fragment_qastudy.*
import kotlinx.android.synthetic.main.fragment_qastudy.banner_plan
import kotlinx.android.synthetic.main.fragment_qatest.*

class QAStudyFragment : Fragment(R.layout.fragment_qastudy) {

    lateinit var viewmodel : PlanViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as HomeActivity
        val favt = activity.findViewById<ImageButton>(R.id.ib_allfavts)
        activity?.supportActionBar?.setTitle(Constants.topicName)
        favt.visibility = View.INVISIBLE

        setBannerAd()

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        val adapter = QAAdapter()
        rv_qastudy.adapter = adapter
        rv_qastudy.layoutManager = LinearLayoutManager(requireContext())

        viewmodel.getQA(Constants.qaCatid,"easy")

        viewmodel.qaLive.observe(viewLifecycleOwner, Observer {
            progressbar_questionstudy.visibility = View.INVISIBLE
            if (it.isEmpty()){
                tv_questionsnotavailable.visibility = View.VISIBLE
            }else{
                tv_questionsnotavailable.visibility = View.INVISIBLE
            }
            adapter.qaList = it
            adapter.notifyDataSetChanged()
        })

        viewmodel.errorQaLive.observe(viewLifecycleOwner, Observer {
            progressbar_questionstudy.visibility = View.INVISIBLE
            Toast.makeText(requireContext(),"Something went wrong $it", Toast.LENGTH_SHORT).show()
        })

        fab_test.setOnClickListener {
            Constants.answerlist = mutableListOf()
            Constants.totalmarks = ""
            Constants.totalpercentage = ""
            Constants.timetaken = ""
            showTestDialog()
        }

    }
    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_plan.loadAd(adRequest)
        } else {
            banner_plan.visibility = View.GONE
        }
    }
    fun showTestDialog(){
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val customview = layoutInflater.inflate(R.layout.dialog_test,null,false)
        alertDialog.setView(customview)
        val startTest = customview.findViewById<Button>(R.id.bt_starttest)
        val radiogroup = customview.findViewById<RadioGroup>(R.id.rg_mode)
        val easy = customview.findViewById<RadioButton>(R.id.rb_easy)
        val difficult = customview.findViewById<RadioButton>(R.id.rb_difficult)
        val min =  customview.findViewById<EditText>(R.id.ed_min)
        startTest.setOnClickListener {
            val minutes = min.text.toString()
            if (minutes.isNotEmpty()){
                val mode = if (easy.isChecked){
                    "easy"
                }else{
                    "diff"
                }
                Constants.easyordiff  = mode
                Constants.testTime = minutes.toInt()
                nav_host_fragment_content_home.findNavController().navigate(R.id.action_QAStudyFragment_to_QATestFragment)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }
}