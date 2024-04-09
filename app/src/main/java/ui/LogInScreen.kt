package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentLogInScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LogInScreen : Fragment() {

    private lateinit var binding: FragmentLogInScreenBinding

    private lateinit var auth: FirebaseAuth




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInScreenBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
        logIn()
        register()
        }

    override fun onStop() {
        super.onStop()
        clearInput()
    }

    fun clearInput(){
        binding.inputEmail.text.clear()
        binding.inputPassword.text.clear()
    }


    fun logIn(){
        binding.logInButton.setOnClickListener {
            var progressBar = binding.logInProgressbar
            progressBar.visibility = View.VISIBLE
            var emailInput = binding.inputEmail.text.toString()
            var passwordInput = binding.inputPassword.text.toString()
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                signInUser(emailInput,passwordInput)
            } else {
                Toast.makeText(binding.root.context, "Bitte mach eine Eingabe !", Toast.LENGTH_SHORT)
                    .show()
                progressBar.visibility = View.GONE
            }
        }

    }

    fun register(){
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.registerScreen)
        }

    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                var progressBar = binding.logInProgressbar
                if (task.isSuccessful) {
                    // Sign in success, navigate to home screen
                    findNavController().navigate(R.id.homeScreen)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(requireContext(), "Anmeldung fehlgeschlagen. Bitte überprüfe deine Eingaben.", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }
    }

}