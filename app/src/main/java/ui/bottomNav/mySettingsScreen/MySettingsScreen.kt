package ui.bottomNav.mySettingsScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ui.logInFlow.LogInScreen


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

    }


    fun logout(){
        binding.optionCard8.setOnClickListener {
            Log.d(TAG,"Logout: + ${auth.currentUser?.email}")
            Log.d(TAG,"Logout: + ${auth.currentUser?.displayName}")
            auth.signOut()
            Log.d(TAG,"Logout: + ${auth.currentUser?.email}")
            Toast.makeText(binding.root.context,requireContext().getString(R.string.toastLogOutHint), Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_mySettings_to_logInScreen)
        }
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

}


