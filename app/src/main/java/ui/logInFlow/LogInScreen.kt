package ui.logInFlow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentLogInScreenBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ui.bottomNav.mySettingsScreen.MySettingsScreen


class LogInScreen : Fragment() {

    private companion object {
        private const val TAG = "LogIn Screen"
        private const val RC_GOOGLE_SIGN_IN = 4926
    }

    private lateinit var binding: FragmentLogInScreenBinding
//    private val firebaseViewModel: FirebaseViewModel by viewModels()

    private lateinit var auth: FirebaseAuth


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
        developerSkip()
        googleLogIn()
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Log.d(TAG,"Log In Screen || On Start: + ${auth.currentUser?.email}")
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null){
       /*     Toast.makeText(
                requireContext(),
                context?.getString(R.string.toastFailedLogIn),
                Toast.LENGTH_SHORT
            ).show()*/
            return
        } else{
            findNavController().navigate(R.id.homeScreen)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val acount = task.getResult(ApiException::class.java)!!
                Log.d(TAG,"firebaseauthGooge: + ${acount.id}")
                firebaseAuthWithGoogle(acount.idToken?:return)
            } catch (e: ApiException) {
                // ...
                Log.w(TAG,"Google anmeldung fehlgeschlagen!")
            }

        }
    }

    private fun firebaseAuthWithGoogle (idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {task->
                if (task.isSuccessful){
                    Log.d(TAG,"signIn with Credential sucessfull")
                    val user = auth.currentUser
                    updateUI(user)
                } else{
                    Log.w(TAG,"signInWithCredential failed",task.exception)
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.toastFailedLogIn),
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }

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
            var progressBar = binding.logInProgressbar
            progressBar.visibility = View.VISIBLE
            var emailInput = binding.inputEmail.text.toString()
            var passwordInput = binding.inputPassword.text.toString()
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                signInUser(emailInput, passwordInput)
            } else {
                Toast.makeText(
                    binding.root.context,
                    context?.getString(R.string.toastUserInputHint),
                    Toast.LENGTH_SHORT
                )
                    .show()
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
        }
    }

    fun register() {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.registerScreen)
        }

    }

//    private fun signInUser(email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                var progressBar = binding.logInProgressbar
//                if (task.isSuccessful) {
//                    // Sign in success, navigate to home screen
//                    val user = auth.currentUser
//                    updateUI(user)
//                    findNavController().navigate(R.id.homeScreen)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        requireContext(),
//                        context?.getString(R.string.toastFailedLogIn),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                progressBar.visibility = View.GONE
//            }
//    }



    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { signInTask ->
                var progressBar = binding.logInProgressbar
                if (signInTask.isSuccessful) {
                    // Sign-in success, check if email is verified
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        // Email is verified, navigate to home screen
                        updateUI(user)
                        findNavController().navigate(R.id.homeScreen)
                    } else {
                        // Email is not verified, prompt user to check email for verification link
                        Toast.makeText(
                            requireContext(),
                            context?.getString(R.string.toastEmailVerificationRequired),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.toastFailedLogIn),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar.visibility = View.GONE
            }
    }



    fun forgotPassword(){
        var forgotPasswordBtn = binding.forgotPasswordView
        forgotPasswordBtn.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordScreen)
        }
    }


//    Diese Methode muss in der App Fertigstellung gelöscht werden!

    fun developerSkip() {
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(R.id.homeScreen)
        }
    }

}
