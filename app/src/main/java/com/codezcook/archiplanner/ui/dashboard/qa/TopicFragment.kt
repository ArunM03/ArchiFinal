package com.codezcook.archiplanner.ui.dashboard.qa

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.adapter.TopicAdapter
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner2.R
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_elevation.*
import kotlinx.android.synthetic.main.fragment_qastudy.*
import kotlinx.android.synthetic.main.fragment_topic.*

class TopicFragment : Fragment(R.layout.fragment_topic) {

    lateinit var viewmodel : PlanViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as HomeActivity
        val favt = activity.findViewById<ImageButton>(R.id.ib_allfavts)
        favt.visibility = View.INVISIBLE

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        setBannerAd()

        val adapter = TopicAdapter()
        rv_topicsqa.adapter = adapter
        rv_topicsqa.layoutManager = LinearLayoutManager(requireContext())

        viewmodel.getTopics()

        adapter.setOnItemClickListener {
            Constants.qaCatid = it.id
            Constants.topicName = it.name
            nav_host_fragment_content_home.findNavController().navigate(R.id.action_topicFragment_to_QAStudyFragment)
        }

        viewmodel.topicLive.observe(viewLifecycleOwner, Observer {
            progressbar_topics.visibility = View.INVISIBLE
            adapter.topicList = it
        })

        viewmodel.errorTopicLive.observe(viewLifecycleOwner, Observer {
            progressbar_topics.visibility = View.INVISIBLE
            Toast.makeText(requireContext(),"Something went wrong $it",Toast.LENGTH_SHORT).show()
        })


    }


    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_qa.loadAd(adRequest)
        } else {
            banner_qa.visibility = View.GONE
        }
    }
}