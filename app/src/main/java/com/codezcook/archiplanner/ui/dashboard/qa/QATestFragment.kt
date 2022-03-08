package com.codezcook.archiplanner.ui.dashboard.qa

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toolbar
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
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_qastudy.*
import kotlinx.android.synthetic.main.fragment_qatest.*
import java.text.DecimalFormat

class QATestFragment : Fragment(R.layout.fragment_qatest) {

    lateinit var viewmodel : PlanViewmodel
    lateinit var countDownTimer: CountDownTimer
    var seconds = 0L
    private var mInterstitialAd: InterstitialAd? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()

        setBannerAd()

        InterstitialAd.load(requireContext(),getString(R.string.qaresult_interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        val activity = activity as HomeActivity
        val favt = activity.findViewById<ImageButton>(R.id.ib_allfavts)
        activity?.supportActionBar?.setTitle(Constants.topicName)
        favt.visibility = View.INVISIBLE

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)

        val adapter = QAAdapter(1)
        rv_questions.adapter = adapter
        rv_questions.layoutManager = LinearLayoutManager(requireContext())

        viewmodel.getQA(Constants.qaCatid,Constants.easyordiff)

        viewmodel.qaLive.observe(viewLifecycleOwner, Observer {
            progressbar_questiontest.visibility = View.INVISIBLE
            adapter.qaList = it
        })

        viewmodel.errorQaLive.observe(viewLifecycleOwner, Observer {
            progressbar_questiontest.visibility = View.INVISIBLE
            Toast.makeText(requireContext(),"Something went wrong $it", Toast.LENGTH_SHORT).show()
        })

        timer()

        bt_submit.setOnClickListener {
            if (!Constants.isSubscribed) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                    showResult()
                }
            }
        }
    }
    private fun setBannerAd(){
        if (!Constants.isSubscribed) {
            val adRequest = AdRequest.Builder().build()
            banner_test.loadAd(adRequest)
        } else {
            banner_test.visibility = View.GONE
        }
    }
    private fun showResult() {
        val answers = Constants.answerlist.filter {it}
    //    Toast.makeText(requireContext(), "correct answer size ${answers} and total answerlist size ${Constants.answerlist}", Toast.LENGTH_SHORT).show()
        var answerdata = if (answers.size < 10) {
            "0${answers.size}"
        } else {
            "${answers.size}"
        }
        Constants.totalmarks = answerdata
        var percent = ((answers.size.toFloat()/Constants.answerlist.size) * 100)
        val floatformat = DecimalFormat("##.##")
        val finaldatadata = floatformat.format(percent)
        val percentdata = "$finaldatadata%"
        Constants.totalpercentage = percentdata
        Constants.totalpercent = percent.toInt()
        Constants.timetaken = timefinal(seconds)
        nav_host_fragment_content_home.findNavController()
            .navigate(R.id.action_QATestFragment_to_resultFragment)
    }

    fun timer(){
        val time = ((Constants.testTime * 60) * 1000).toLong()
       countDownTimer = object : CountDownTimer(time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                seconds = time - millisUntilFinished
                val finaltime = timefinal(millisUntilFinished)
                chronometer.text = finaltime
            }

            override fun onFinish() {
                Toast.makeText(requireContext(),"Time finished",Toast.LENGTH_SHORT).show()
            }
        };
        countDownTimer.start()
    }

    private fun timefinal(millisUntilFinished: Long): String {
        val min = ((millisUntilFinished / 1000) / 60).toInt()
        val sec = ((millisUntilFinished / 1000) % 60).toInt()
        val finaltime = finaltime(min, sec)
        return finaltime
    }

    private fun finaltime(min: Int, sec: Int): String {
        val minFormat = getMin(min)
        val secFormat = getSec(sec)
        val finaltime = "$minFormat:$secFormat"
        return finaltime
    }

    private fun getMin(min: Int): String {
        val minFormat = getSec(min)
        return minFormat
    }

    private fun getSec(sec: Int): String {
        val secFormat = if (sec == 0) {
            "00"
        } else if (sec < 10) {
            "0$sec"
        } else {
            "$sec"
        }
        return secFormat
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStop() {
        countDownTimer.cancel()
        super.onStop()
    }
}