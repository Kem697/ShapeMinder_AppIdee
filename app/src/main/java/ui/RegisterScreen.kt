package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentRegisterScreenBinding


class RegisterScreen : Fragment() {
    private lateinit var binding: FragmentRegisterScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register()
        backNav()
    }



    fun backNav(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun register(){
        binding.submitButton.setOnClickListener {
            var nameInput = binding.inputName.text.toString()
            var emailInput = binding.inputEmail.text.toString()
            var passwordInput = binding.inputPassword.text.toString()
            var passwordRepeatInput = binding.inputPasswordRepeat.text.toString()
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()
                && nameInput.isNotBlank() && passwordRepeatInput.isNotBlank()
                && passwordInput == passwordRepeatInput) {
                binding.inputEmail.text.clear()
                binding.inputPassword.text.clear()
                findNavController().navigate(R.id.homeScreen)
            } else {
                Toast.makeText(binding.root.context, "Bitte mach eine Eingabe !", Toast.LENGTH_SHORT)
                    .show()
            }
        }



    }



}