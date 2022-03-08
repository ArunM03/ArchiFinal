package com.codezcook.archiplanner.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.HomeActivity
import com.codezcook.archiplanner.api.PlanViewmodel
import com.codezcook.archiplanner.sharedpref.SharedPref
import com.codezcook.archiplanner2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_otp.*
import java.util.*

@Suppress("DEPRECATION")
class OtpFragment : Fragment(R.layout.fragment_otp) {

    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var progressDialog : ProgressDialog
    lateinit var viewmodel : PlanViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(PlanViewmodel::class.java)



        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Verifying Otp...")

        viewmodel.userCreatedLive.observe(viewLifecycleOwner, Observer {
            SharedPref(requireContext()).saveUserId(Constants.userData.id)
            progressDialog.dismiss()
            Toast.makeText(requireContext(),"Successfully Logged In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(),HomeActivity::class.java))
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })

        viewmodel.errorUserCreatedLive.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })

        viewmodel.createUserResponseLive.observe(viewLifecycleOwner, Observer {
            viewmodel.createPlayer(Constants.userData)
        //    progressDialog.dismiss()
            //Toast.makeText(requireContext(),"Successfully Logged In", Toast.LENGTH_SHORT).show()
        /*    SharedPref(requireContext()).saveJoinedDate(Calendar.getInstance().timeInMillis.toString())
            SharedPref(requireContext()).addFixedCount(30)*/

        })

        viewmodel.errorCreateUserDetailsLive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        })


        cd_verifyotp.setOnClickListener {
            if (ed_otp.text.isNotEmpty()){
                progressDialog.setMessage("Verifying the OTP")
                progressDialog.show()
                verifyVerficationcode(ed_otp.text.toString())
            }else{
                Toast.makeText(requireContext(),"Please enter your OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun verifyVerficationcode(code : String){
        val credential = PhoneAuthProvider.getCredential(Constants.storedverificationId, code)
        signUp(credential)
    }

    fun signUp(credential: PhoneAuthCredential){
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    viewmodel.createUserDetails(firebaseAuth.currentUser?.uid!!,Constants.nameLogin,Constants.mobileLogin,Constants.emailLogin)
        //         Toast.makeText(requireContext(),"${firebaseAuth.currentUser?.uid!!} ${Constants.nameLogin} ${Constants.mobileLogin} ${Constants.emailLogin}",Toast.LENGTH_LONG).show()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(),"OTP wrong", Toast.LENGTH_SHORT).show()
                    }
                    // Update UI
                }
            }
    }
}