package de.kem697.shapeminder.ui.logInFlow

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentForgotPasswordScreenBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordScreen : Fragment() {
    private lateinit var binding : FragmentForgotPasswordScreenBinding
    private lateinit var userEmail: String
    private lateinit var auth: FirebaseAuth




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordScreenBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startResetPasswordRequest()
    }



    fun startResetPasswordRequest(){
        val resetPasswordBtn = binding.resetPasswordButton
        resetPasswordBtn.setOnClickListener {
            val editEmail = binding.inputEmail
            val userInput = editEmail.text.toString()
            userEmail = userInput
            if (!TextUtils.isEmpty(userInput)){
                val tag = "Forgot PW"
                Log.i(tag, "Email: $userEmail")
                resetPassword()
            } else {
                Toast.makeText(binding.root.context, context?.getString(R.string.toastUserInputHint), Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun resetPassword(){
        var pogressbar = binding.forgotPasswordProgressbar
        pogressbar.visibility = View.VISIBLE
        binding.resetPasswordButton.visibility = Button.INVISIBLE
        auth.sendPasswordResetEmail(userEmail)
            .addOnSuccessListener {task->
                Toast.makeText(binding.root.context, requireContext().getString(R.string.toastResetPasswordLinkSentHint), Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.logInScreen)
                pogressbar.visibility = View.VISIBLE
            }
            .addOnFailureListener {task->
                Toast.makeText(binding.root.context, requireContext().getString(R.string.toastUnknownFailure), Toast.LENGTH_SHORT)
                    .show()
        }
            .addOnCompleteListener {task->
                binding.resetPasswordButton.visibility = Button.VISIBLE
                pogressbar.visibility = View.GONE
            }
    }


}