package ui.logInFlow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentRegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import ui.viewModel.GoogleFireBaseViewModel


class RegisterScreen : Fragment() {
    private lateinit var binding: FragmentRegisterScreenBinding


    private val googleFireBaseViewModel: GoogleFireBaseViewModel by viewModels()

    private val fireStore = FirebaseFirestore.getInstance()

    lateinit var profileRef: DocumentReference



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
//        navigateBack()
    }

    override fun onStop() {
        super.onStop()
        clearInput()
    }


/*
    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
*/

    fun register() {
        binding.submitButton.setOnClickListener {
            var progressbar = binding.registerProgressbar
            var nameInput = binding.inputName.text.toString()
            var emailInput = binding.inputEmail.text.toString()
            var passwordInput = binding.inputPassword.text.toString()
            var passwordRepeatInput = binding.inputPasswordRepeat.text.toString()

            googleFireBaseViewModel.fireBaseRegister(nameInput,emailInput,passwordInput,passwordRepeatInput,auth)
            progressbar.visibility = View.VISIBLE

            googleFireBaseViewModel.registrationResult.observe(viewLifecycleOwner) { isSuccess ->
                if (isSuccess) {
                    // Erfolgreiche Registrierung
                    findNavController().navigate(R.id.logInScreen)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toastSuccesfulAccountCreation),
                        Toast.LENGTH_SHORT
                    ).show()
                    progressbar.visibility = View.GONE

                } else {
                    // Fehler bei der Registrierung
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toastFailedAccountCreation),
                        Toast.LENGTH_SHORT
                    ).show()
                    progressbar.visibility = View.GONE
                }
            }
        }
    }


    fun clearInput(){
        binding.inputEmail.text.clear()
        binding.inputPassword.text.clear()
    }


}