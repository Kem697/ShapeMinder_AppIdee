package de.kem697.shapeminder.ui.bottomNav.mySettingsScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentSettingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import de.kem697.shapeminder.ui.viewModel.GoogleFireBaseViewModel


class MySettingsScreen : Fragment() {


    private companion object {
        private const val TAG = "LogIn Screen"
    }



    private lateinit var binding: FragmentSettingsBinding
    private lateinit var auth: FirebaseAuth
    private val googleFireBaseViewModel: GoogleFireBaseViewModel by viewModels()
    var darkModeOn = false

    private val changeImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { imageUri ->
                requireContext().contentResolver.takePersistableUriPermission(
                    imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )


                Log.i("ProfileImage", "Image Uri : ${auth.currentUser?.photoUrl.toString()}")
                googleFireBaseViewModel.uploadImage(imageUri)
                Toast.makeText(context,
                    getString(R.string.toastChangeProfilePicHint),Toast.LENGTH_SHORT).show()



            }
        }
    }



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
        setUpUI()

    }

    fun setUpUI(){
        googleFireBaseViewModel.imageUploadSuccess.observe(viewLifecycleOwner){
            binding.profileImg.load(it)
        }
        logout()
        personalSettings()
        shareApp()
        modeSwitch()
        changeProfileImage()
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
//                binding.profileImg.setImageURI(googleFireBaseViewModel.imageUploadSuccess.value)
                /*Hiermit soll das Profilbild hochgeladen werden.
                * Bei einem Account wird das Bild angezeigt, bei einem anderen nicht.
                * Vielleicht liegt es am Bildformat?*/
            }
        googleFireBaseViewModel.currentUser()
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


    fun changeProfileImage(){
        var profileImage = binding.profileImg
        profileImage.setOnClickListener {
            val openGalleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            changeImage.launch(openGalleryIntent)
        }
    }





}


