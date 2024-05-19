package ui.logInFlow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentLogInScreenBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import ui.viewModel.GoogleFireBaseViewModel
import java.util.Locale


class LogInScreen : Fragment() {

    private companion object {
        private const val TAG = "LogIn Screen"
        private const val RC_GOOGLE_SIGN_IN = 4926
    }

    private lateinit var binding: FragmentLogInScreenBinding

    private val googleFireBaseViewModel: GoogleFireBaseViewModel by viewModels()

    private lateinit var auth: FirebaseAuth

    private val fireStore = FirebaseFirestore.getInstance()



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
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
        forgotPassword()
        logIn()
        register()
//        developerSkip()
        googleLogIn()
        changeInAppLanguage()
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Log.d(TAG, "Log In Screen || On Start: + ${auth.currentUser?.email}")
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            return
        } else {
            findNavController().navigate(R.id.action_logInScreen_to_homeScreen)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val acount = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseauthGooge: + ${acount.id}")
                firebaseAuthWithGoogle(acount.idToken ?: return)
            } catch (e: ApiException) {
                // ...
                Log.w(TAG, "Google anmeldung fehlgeschlagen!")
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        googleFireBaseViewModel.firebaseAuthWithGoogle(idToken, auth,fireStore)
    }


    override fun onStop() {
        super.onStop()
        clearInput()
    }


    fun clearInput() {
        binding.inputEmail.text.clear()
        binding.inputPassword.text.clear()
    }


    fun logIn() {
        binding.logInButton.setOnClickListener {
            val progressBar = binding.logInProgressbar
            progressBar.visibility = View.VISIBLE
            val emailInput = binding.inputEmail.text.toString()
            val passwordInput = binding.inputPassword.text.toString()

            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                // Erst alle vorherigen Observer entfernen, um Mehrfachausführungen zu vermeiden
                googleFireBaseViewModel.signInResult.removeObservers(viewLifecycleOwner)

                googleFireBaseViewModel.fireBaseSignInUser(emailInput, passwordInput, auth)

                googleFireBaseViewModel.signInResult.observe(viewLifecycleOwner) { isSuccess ->
                    progressBar.visibility = View.GONE
                    val user = auth.currentUser
                    if (isSuccess) {
                        if (user != null && user.isEmailVerified) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.toastSucessfulLogIn),
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_logInScreen_to_homeScreen)
                        }
                    } else if (user!=null && !user.isEmailVerified) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.toastEmailVerificationRequired),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(
                    binding.root.context,
                    context?.getString(R.string.toastUserInputHint),
                    Toast.LENGTH_SHORT
                ).show()
                progressBar.visibility = View.GONE
            }
        }
    }


    fun googleLogIn() {
        var signInRequest = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(requireContext(), signInRequest)
        var googleSignInBtn = binding.googleSignInBtn
        googleSignInBtn.setOnClickListener {
            val signInIntent = client.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)

            googleFireBaseViewModel.googleSignInResult.observe(viewLifecycleOwner) { isSuccess ->
                if (isSuccess) {
                    findNavController().navigate(R.id.action_logInScreen_to_homeScreen)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toastFailedLogIn),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.logInProgressbar.visibility = View.GONE
            }
        }
    }

    fun register() {
            binding.registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_logInScreen_to_registerScreen)
            }

        }

    fun forgotPassword() {
            var forgotPasswordBtn = binding.forgotPasswordView
            forgotPasswordBtn.setOnClickListener {
                findNavController().navigate(R.id.action_logInScreen_to_forgotPasswordScreen)
            }
        }



//    I need to improve this

    fun changeInAppLanguage(){
        var setLanguageSpinner = binding.languageSpinner
        val currentLocale = Locale.getDefault().language
        val languageOptions = mapOf(
            "de" to requireContext().getString(R.string.language_german),
            "en" to requireContext().getString(R.string.language_english),
            "tr" to requireContext().getString(R.string.language_turkish),
            "nb" to requireContext().getString(R.string.language_norwegian)
        )


        val languages = mutableListOf<String>()
        val currentLanguageText = languageOptions[currentLocale] ?: getString(R.string.language_english)
        languages.add(currentLanguageText)

        for ((code, name) in languageOptions) {
            if (code != currentLocale) {
                languages.add(name)
            }
        }


        var tag = "Sprache??"
        Log.i(tag,"Aktuelle Sprache: $currentLocale" )




        var adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item,languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        setLanguageSpinner.adapter = adapter
        setLanguageSpinner.setSelection(0)

        setLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguageCode = when (position) {
                    0 -> currentLocale // First item is always the current locale
                    else -> languageOptions.keys.toList()[position]
                }

                var tag = "Sprache??"
                Log.i(tag,"Kürzel: $selectedLanguageCode| Position : $position | Aktuelle Sprache: $currentLocale" )


                if (currentLocale != selectedLanguageCode) {
                    setLocale(selectedLanguageCode)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setLocale(languageCode: String) {

        val currentLocale = resources.configuration.locale.language
        if (currentLocale == languageCode) {
            // If the selected language is the same as the current language, do nothing
            return
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        Locale.setDefault(locale)
        resources.updateConfiguration(config, resources.displayMetrics)


        // Restart the activity to apply the new language settings
        val refresh = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().finish()
        startActivity(refresh)



    }


//    Diese Methode muss in der App Fertigstellung gelöscht werden!

   /* fun developerSkip() {
            binding.skipBtn.setOnClickListener {
                findNavController().navigate(R.id.homeScreen)
            }
        }*/

    }

