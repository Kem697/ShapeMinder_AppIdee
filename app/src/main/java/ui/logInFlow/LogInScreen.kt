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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentLogInScreenBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ui.viewModel.GoogleFireBaseViewModel


class LogInScreen : Fragment() {

    private companion object {
        private const val TAG = "LogIn Screen"
        private const val RC_GOOGLE_SIGN_IN = 4926
    }

    private lateinit var binding: FragmentLogInScreenBinding

    private val googleFireBaseViewModel: GoogleFireBaseViewModel by viewModels()

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
        Log.d(TAG, "Log In Screen || On Start: + ${auth.currentUser?.email}")
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            return
        } else {
            findNavController().navigate(R.id.homeScreen)
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
        googleFireBaseViewModel.firebaseAuthWithGoogle(idToken, auth)
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

                googleFireBaseViewModel.fireBaseSignInUser(emailInput, passwordInput, auth)

                googleFireBaseViewModel.signInResult.observe(viewLifecycleOwner) { isSuccess ->
                    if (isSuccess) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.toastSucessfulLogIn),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.homeScreen)
                    }   else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.toastFailedLogIn),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    binding.logInProgressbar.visibility = View.GONE
                }

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

            googleFireBaseViewModel.googleSignInResult.observe(viewLifecycleOwner) { isSuccess ->
                if (isSuccess) {
                    findNavController().navigate(R.id.homeScreen)
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
                findNavController().navigate(R.id.registerScreen)
            }

        }

    fun forgotPassword() {
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

