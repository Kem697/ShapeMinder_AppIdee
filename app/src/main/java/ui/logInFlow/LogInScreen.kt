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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
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



    /*EN:
    This methode serves to allow the user to change the in app language
    in the login screen. The intent is to improve the user experience from specific
    point of views. For example the in app language change is more accessible for
    users, because they do not delve into system settings. Besides providing an in-app language
    switch demonstrates cultural sensitivity and respect for the user's language preferences.
    */

    fun changeInAppLanguage() {
        val setLanguageSpinner = binding.languageSpinner
        var currentLocale = Locale.getDefault().language
        val languageOptions = mapOf(
            "de" to getString(R.string.language_german),
            "en" to getString(R.string.language_english),
            "fr" to getString(R.string.language_french),
            "tr" to getString(R.string.language_turkish),
            "nb" to getString(R.string.language_norwegian)
        )

        val languages = mutableListOf(getString(R.string.selectLanguageHint)).apply {
            addAll(languageOptions.values)
        }

        val tag = "LanguageSelection"

        Log.i(tag, "Current language: $currentLocale")

        val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.list_item_language, languages) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_language, parent, false)
                val languageText = view.findViewById<TextView>(R.id.languageName)
                val languageImage = view.findViewById<ImageView>(R.id.languageImage)

                languageText.text = getItem(position)
                if (position != 0) {
                    val languageCode = languageOptions.keys.elementAt(position - 1)
                    val drawableRes = when (languageCode) {
                        "de" -> R.drawable.de_icon
                        "en" -> R.drawable.us_icon
                        "fr" -> R.drawable.fr_icon
                        "tr" -> R.drawable.tr_icon
                        "nb" -> R.drawable.no_icon
                        else -> R.drawable.earth_icon
                    }
                    languageImage.setImageResource(drawableRes)
                } else {
                    languageImage.setImageResource(R.drawable.earth_icon)
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return getView(position, convertView, parent)
            }
        }

        adapter.setDropDownViewResource(R.layout.list_item_language)
        setLanguageSpinner.adapter = adapter

        setLanguageSpinner.setSelection(0)

        setLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) return

                val selectedLanguageCode = languageOptions.keys.elementAt(position - 1)
                Log.i(tag, "Code: $selectedLanguageCode | Position: $position | New language: ${languageOptions[selectedLanguageCode]}")

                if (currentLocale != selectedLanguageCode) {
                    setLocale(selectedLanguageCode)
                    currentLocale = selectedLanguageCode
                    refreshActivity(MainActivity())
                    Log.i(tag, "Language changed to: $currentLocale")
                } else{
                    setLocale(selectedLanguageCode)
                    refreshActivity(MainActivity())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun refreshActivity(activity: MainActivity){
        val refresh = Intent(requireActivity(), activity::class.java)
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(refresh)
        requireActivity().finish()

    }



//    Diese Methode muss in der App Fertigstellung gelöscht werden!

   /* fun developerSkip() {
            binding.skipBtn.setOnClickListener {
                findNavController().navigate(R.id.homeScreen)
            }
        }*/

    }

