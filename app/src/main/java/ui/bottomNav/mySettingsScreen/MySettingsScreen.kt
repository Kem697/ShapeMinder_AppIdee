package ui.bottomNav.mySettingsScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentSettingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class MySettingsScreen : Fragment() {

    private lateinit var binding: FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout()
        personalSettings()
        shareApp()

    }


    fun logout(){
        binding.optionCard8.setOnClickListener {
            findNavController().navigate(R.id.logInScreen)
        }
    }


    fun personalSettings(){
            Firebase.auth.currentUser?.let { user ->
                binding.userName.text = user.displayName
                binding.userEmail.text = user.email
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
}


