package de.kem697.shapeminder.ui.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.kem697.shapeminder.model.Profile

class GoogleFireBaseViewModel : ViewModel() {

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

    private val _imageUploadSuccess = MutableLiveData<Uri>()
    val imageUploadSuccess : LiveData<Uri>

        get() = _imageUploadSuccess


    init {
        if (firebaseAuth.currentUser != null) {
            profileRef = fireStore.collection("profiles").document(firebaseAuth.currentUser!!.uid)
        }
    }

    private fun setUserImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString())
    }


    fun uploadImage(uri: Uri) {
        _imageUploadSuccess.postValue(uri)
        val firebaseAuth = FirebaseAuth.getInstance() // Ensure you have initialized Firebase Auth

        // Creating a reference and the upload task
        val imageRef = storageRef.child("profiles/${firebaseAuth.currentUser!!.uid}/profilePicture")
        val uploadTask = imageRef.putFile(uri)

        // When UploadTask is executed and successful, pass the download URL of the image to the setUserImage function
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener { uriTask ->
                    if (uriTask.isSuccessful) {
                        val downloadUri = uriTask.result
                        updateUserProfile(downloadUri)

                    } else {
                        // Handle failures
//                        Toast.makeText(this, "Failed to retrieve download URL", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Handle failures
//                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fireBaseRegister(
        nameInput: String,
        emailInput: String,
        passwordInput: String,
        passwordRepeatInput: String,
        auth: FirebaseAuth,
    ) {
        if (emailInput.isNotBlank() && emailInput.contains("@")
            && passwordInput.isNotBlank()
            && nameInput.isNotBlank()
            && passwordRepeatInput.isNotBlank()
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
                                        ?.addOnCompleteListener {
                                            emailverification->
                                            if (emailverification.isSuccessful){
                                                _registrationResult.postValue(true)
                                                val profileRef =
                                                    fireStore.collection("profiles").document(auth.currentUser!!.uid)
                                                profileRef.set(
                                                    Profile(
                                                        nameInput
                                                    )
                                                ).addOnCompleteListener {
                                                    task->
                                                    if (task.isSuccessful){
                                                        var tag ="Profile Referenzen"
                                                        Log.i("$tag"," Erfolg: ${profileRef.id}")
                                                    }
                                                }
                                                auth.signOut()
                                            } else{
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
        } else {
            _registrationResult.postValue(false)
        }
    }


    private fun updateUserProfile(downloadUrl: Uri) {
        val user = firebaseAuth.currentUser ?: return
        val profileUpdates = userProfileChangeRequest {
            photoUri = downloadUrl
        }
        user.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Profile", "User profile updated.")
                setUserImage(downloadUrl)
                uploadImageToFirebaseStorage(downloadUrl)

            }
        }
    }

    fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val fileRef = storageRef.child("profile.jpg")

        Log.i("ProfileImage", "Starting upload to Firebase Storage...")
        fileRef.putFile(imageUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("ProfileImage", "Upload successful!")
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        Log.i("ProfileImage", "File available at: $uri")
                        _imageUploadSuccess.postValue(uri)
                    }
                } else {
                    Log.e("ProfileImage", "Upload failed: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileImage", "Upload failed with exception: ${exception.message}")
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

    fun firebaseAuthWithGoogle(idToken: String, auth: FirebaseAuth, fireStore: FirebaseFirestore) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val profileRef = fireStore.collection("profiles").document(user.uid)
                        profileRef.get().addOnSuccessListener { document ->
                            if (!document.exists()) {
                                val profile = Profile(
                                    user.displayName ?: "Unknown"
                                ) // Handling null displayName
                                profileRef.set(profile)
                                    .addOnSuccessListener {
                                        _googleSignInResult.postValue(true)
                                    }
                                    .addOnFailureListener {
                                        _googleSignInResult.postValue(false)
                                    }
                            } else {
                                _googleSignInResult.postValue(true)
                            }
                        }.addOnFailureListener {
                            _googleSignInResult.postValue(false)
                        }
                    } else {
                        _googleSignInResult.postValue(false)
                    }
                } else {
                    _googleSignInResult.postValue(false)
                }
            }
    }


    fun currentUser(){
        var currentUser = firebaseAuth.currentUser
        if (currentUser!=null){
            _imageUploadSuccess.postValue(currentUser.photoUrl)
        }
    }


}