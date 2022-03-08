package com.codezcook.archiplanner.login

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner2.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.fragment_mobile.*
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class MobileFragment : Fragment(R.layout.fragment_mobile) {

    lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId : String
    lateinit var progressDialog : ProgressDialog
    lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    val firebaseAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Waiting for the Otp...")

      //  val newdata = "sdklfjl".to


        cd_createaccount.setOnClickListener {
            val phoneNo = ed_mobilenumber.text.toString()
            val email = ed_email.text.toString()
            val username = ed_username.text.toString()
            if (phoneNo.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty()){
                Constants.emailLogin = email
                Constants.mobileLogin = phoneNo
                Constants.nameLogin = username
                Constants.userData.apply {
                    this.id  = phoneNo
                    this.mobile  = phoneNo
                    this.TotalCount = 30
                    this.curCount = 0
                    this.plan = "Free"
                    this.username = username
                }
                sendOtp("+91$phoneNo")
                progressDialog.show()
            }else{
                Toast.makeText(requireContext(),"Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        callbacks  = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                progressDialog.dismiss()
                Toast.makeText(requireContext(),"Verification Failed ${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                Constants.storedverificationId = verificationId
                resendToken = token
                progressDialog.dismiss()
                fragmentContainerView4.findNavController().navigate(R.id.action_mobileFragment_to_otpFragment)
            }
        }

    }


    fun sendOtp(phoneNo : String){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNo,
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            callbacks
        )
    }
}