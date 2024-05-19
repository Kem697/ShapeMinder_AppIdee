package ui.bottomNav.mySettingsScreen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ui.logInFlow.LogInScreen
import java.util.Locale


class MySettingsScreen : Fragment() {


    private companion object {
        private const val TAG = "LogIn Screen"
    }



    private lateinit var binding: FragmentSettingsBinding
    private lateinit var auth: FirebaseAuth

    var darkModeOn = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout()
        personalSettings()
        shareApp()
        modeSwitch()
//        changeInAppLanguage()

    }

    override fun onStop() {
        super.onStop()

    }

    fun logout(){
        binding.optionCard8.setOnClickListener {
            Log.d(TAG,"Logout: + ${auth.currentUser?.email}")
            Log.d(TAG,"Logout: + ${auth.currentUser?.displayName}")
            auth.signOut()
            Log.d(TAG,"Logout: + ${auth.currentUser?.email}")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkModeOn = false



            Toast.makeText(binding.root.context,requireContext().getString(R.string.toastLogOutHint), Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_mySettings_to_logInScreen)
        }
        binding.darkLightModeToogle.text = requireContext().getString(R.string.lightModeText)
    }


    fun personalSettings(){
            Firebase.auth.currentUser?.let { user ->
                binding.userName.text = user.displayName
                binding.userEmail.text = user.email
                /*Hiermit soll das Profilbild hochgeladen werden.
                * Bei einem Account wird das Bild angezeigt, bei einem anderen nicht.
                * Vielleicht liegt es am Bildformat?*/
                if (user.photoUrl!=null){
                    binding.profileImg.load(user.photoUrl)
                } else{
//                    binding.profileImg.setImageResource(R.drawable.account_icon)
                }
            }
    }


    fun shareApp(){
        var shareToFriendsBTn = binding.optionCard7
        shareToFriendsBTn.setOnClickListener {
            //        var appUrl = "https://play.google.com/store/apps/details?id=\" + BuildConfig.APPLICATION_ID"
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.putExtra(
                Intent.EXTRA_TITLE,
                getString(R.string.recommendFriendsText)
            )
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Url")
            val chooser = Intent.createChooser(sendIntent, getString(R.string.intentShareText))
            requireActivity().startActivity(chooser)

        }
    }


    /*EN:
    * This function is the implementation of the dark/light mode switch in my app.
    * When the user switches the toggle, the modes of app will change there the
    * user get different design. the sate of a variable, which pose
    * the dark/light mode state of the, */
    fun modeSwitch() {
        val modeSwitchToggle = binding.darkLightModeToogle

        modeSwitchToggle.setOnClickListener {
            if (darkModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                modeSwitchToggle.text = requireContext().getString(R.string.lightModeText)
                darkModeOn = false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                modeSwitchToggle.text = requireContext().getString(R.string.darkModeText)
                darkModeOn = true
            }
        }

        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                darkModeOn = false
                modeSwitchToggle.text = requireContext().getString(R.string.lightModeText)
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                darkModeOn = true
                modeSwitchToggle.text = requireContext().getString(R.string.darkModeText)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeOn = false
                modeSwitchToggle.text = requireContext().getString(R.string.lightModeText)
            }
        }
    }


//    I need to improve this function, because it doesnt work correctly.
 /*   fun changeInAppLanguage(){
        var setLanguageSpinner = binding.languageSpinner
        val currentLocale = Locale.getDefault().language
        val languageOptions = mapOf(
            "en" to requireContext().getString(R.string.language_english),
            "de" to requireContext().getString(R.string.language_german),
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
        resources.updateConfiguration(config, resources.displayMetrics)


        // Restart the activity to apply the new language settings
//        val refresh = Intent(requireActivity(), MainActivity::class.java)
//        startActivity(refresh)
//        requireActivity().finish()

//        findNavController().navigate(R.id.action_mySettings_to_homeScreen)

    }*/


}


