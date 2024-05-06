package ui.logInFlow

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentForgotPasswordScreenBinding
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
        navigateBack()
        startResetPasswordRequest()
    }


    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun startResetPasswordRequest(){
        val resetPasswordBtn = binding.resetPasswordButton
        resetPasswordBtn.setOnClickListener {
            val editEmail = binding.inputEmail
            val userInput = editEmail.text.toString()
            userEmail = userInput
            if (!TextUtils.isEmpty(userInput)){
                resetPassword()
            } else {
                Toast.makeText(binding.root.context, context?.getString(R.string.toastUserInputHint), Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun resetPassword(){
        var editEmail = binding.inputEmail
        var pogressbar = binding.forgotPasswordProgressbar
        pogressbar.visibility = View.GONE
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
                binding.resetPasswordButton.visibility = Button.INVISIBLE
                pogressbar.visibility = View.VISIBLE
            }
    }


}