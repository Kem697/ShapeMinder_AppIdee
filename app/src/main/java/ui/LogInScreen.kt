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


class LogInScreen : Fragment() {

    private lateinit var binding: FragmentLogInScreenBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInScreenBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
        binding.logInButton.setOnClickListener {
            var emailInput = binding.inputEmail.text.toString()
            var passwordInput = binding.inputPassword.text.toString()
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                binding.inputEmail.text.clear()
                binding.inputPassword.text.clear()
                findNavController().navigate(R.id.homeScreen)
            } else {
                Toast.makeText(binding.root.context, "Bitte mach eine Eingabe !", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.registerScreen)
        }

    }







}