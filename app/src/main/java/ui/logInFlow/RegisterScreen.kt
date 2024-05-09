package ui.logInFlow

import FirebaseViewModel
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import model.Profile


class RegisterScreen : Fragment() {
    private lateinit var binding: FragmentRegisterScreenBinding

    private val firebaseViewModel: FirebaseViewModel by viewModels()

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
                            val user = auth.currentUser
                            /*Hier werden die Profildaten anhand der Usereingaben gesetzt.*/
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(nameInput)
                                .build()
                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { profileTask ->
                                    if (profileTask.isSuccessful) {
                                        auth.currentUser?.sendEmailVerification()
                                        profileRef = fireStore.collection("profiles").document(auth.currentUser!!.uid)
                                        profileRef.set(Profile(nameInput))
                                        auth.signOut()
                                        findNavController().navigate(R.id.logInScreen)
                                        Log.d(tag, "Nutzerkonto angelegt")
                                        Toast.makeText(
                                            binding.root.context,
                                            context?.getString(R.string.toastSuccesfulAccountCreation),
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    } else {
                                        Log.w(tag, "Fehler beim Aktualisieren des Anzeigenamens", profileTask.exception)
                                    }
                                }
                        } else {
                            Log.w(tag, "Nutzerkonto konnte nicht angelegt werden", task.exception)
                            Toast.makeText(
                                binding.root.context,
                                context?.getString(R.string.toastFailedAccountCreation),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

            } else {
                Toast.makeText(binding.root.context, context?.getString(R.string.toastUserInputHint), Toast.LENGTH_SHORT)
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