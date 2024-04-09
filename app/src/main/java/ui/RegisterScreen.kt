package ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentRegisterScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class RegisterScreen : Fragment() {
    private lateinit var binding: FragmentRegisterScreenBinding

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register()
        navigateBack()
    }

    override fun onStop() {
        super.onStop()
        clearInput()
    }



    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun register(){
        binding.submitButton.setOnClickListener {
            var progressbar = binding.registerProgressbar
            var nameInput = binding.inputName.text.toString()
            var emailInput = binding.inputEmail.text.toString()
            var passwordInput = binding.inputPassword.text.toString()
            var passwordRepeatInput = binding.inputPasswordRepeat.text.toString()
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()
                && nameInput.isNotBlank() && passwordRepeatInput.isNotBlank()
                && passwordInput == passwordRepeatInput) {
                progressbar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener { task ->
                        var tag = "Registrierung?"
                        progressbar.visibility = View.GONE
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.homeScreen)
                            Log.w(tag, "createUserWithEmail:Account created", task.exception)
                            Toast.makeText(
                                binding.root.context,
                                "Authentication successful.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        } else {
                            Log.w(tag, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                binding.root.context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(binding.root.context, "Bitte mach eine Eingabe !", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

        }



    }


    fun clearInput(){
        binding.inputEmail.text.clear()
        binding.inputPassword.text.clear()
    }







}