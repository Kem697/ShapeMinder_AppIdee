package ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import model.Profile

class GoogleFireBaseViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    // Instanz von Firebase Firestore
    private val fireStore = FirebaseFirestore.getInstance()

    // Instanz und Referenz von Firebase Storage
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    lateinit var profileRef: DocumentReference


    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean>
        get() = _registrationResult

    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean>
        get() = _signInResult


    private val _googleSignInResult = MutableLiveData<Boolean>()
    val googleSignInResult: LiveData<Boolean>
        get() = _googleSignInResult


    init {
        if (firebaseAuth.currentUser != null) {
            profileRef = fireStore.collection("profiles").document(firebaseAuth.currentUser!!.uid)
        }
    }

    private fun setUserImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString())
    }


    fun uploadImage(uri: Uri) {
        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("images/${firebaseAuth.currentUser!!.uid}/profilePic")
        val uploadTask = imageRef.putFile(uri)

        // Wenn UploadTask ausgeführt und erfolgreich ist, wird die Download-Url des Bildes an die setUserImage Funktion weitergegeben
        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    setUserImage(it.result)
                }
            }
        }
    }

    fun fireBaseRegister(
        nameInput: String,
        emailInput: String,
        passwordInput: String,
        passwordRepeatInput: String,
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ) {
        if (emailInput.isNotBlank() && passwordInput.isNotBlank()
            && nameInput.isNotBlank() && passwordRepeatInput.isNotBlank()
            && passwordInput == passwordRepeatInput
        ) {
            auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(nameInput)
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    auth.currentUser?.sendEmailVerification()
                                    val profileRef =
                                        fireStore.collection("profiles").document(auth.currentUser!!.uid)
                                    profileRef.set(Profile(nameInput))
                                    auth.signOut()
                                    _registrationResult.postValue(true)
                                } else {
                                    _registrationResult.postValue(false)
                                }
                            }
                    } else {
                        _registrationResult.postValue(false)
                    }
                }
        } else {
            _registrationResult.postValue(false)
        }
    }


    fun fireBaseSignInUser(email: String, password: String, auth: FirebaseAuth) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    val user = auth.currentUser
                    _signInResult.postValue(user?.isEmailVerified == true)
                } else {
                    _signInResult.postValue(false)
                }
            }
    }

    fun firebaseAuthWithGoogle(idToken: String, auth: FirebaseAuth) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _googleSignInResult.postValue(user != null)
                } else {
                    _googleSignInResult.postValue(false)
                }
            }
    }

}