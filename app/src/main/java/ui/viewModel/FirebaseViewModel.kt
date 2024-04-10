
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import model.Profile

class FirebaseViewModel: ViewModel() {

    // Instanz von Firebase Authentication
    // Ersetzt in diesem Fall ein Repository
    private val firebaseAuth = FirebaseAuth.getInstance()

    // Instanz von Firebase Firestore
    private val fireStore = FirebaseFirestore.getInstance()

    // Instanz und Referenz von Firebase Storage
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    // LiveData um den aktuellen User zu halten
    // Initialwert ist in diesem Fall firebaseAuth.currentUser
    // Das gewährleistet, dass der User sofort wieder eingeloggt ist sollte er sich bereits einmal eingeloggt haben
    // LiveData kann auch "null" sein (Wenn der User nicht eingeloggt ist)
    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    // profileRef ist lateinit, da sie vom currentUser abhängt, wird gesetzt sobald User eingeloggt wird
    lateinit var profileRef: DocumentReference

    // Statische Referenz auf die Notes Collection
    val notesRef = fireStore.collection("notes")

    // "Cookie-Funktion": Wenn ein User die App startet und bereits eingeloggt ist
    // Dann müssen wir auch die profileRef Variable setzen, damit App nicht abstürzt
    init {
        if (firebaseAuth.currentUser != null) {
            profileRef = fireStore.collection("profiles").document(firebaseAuth.currentUser!!.uid)
        }
    }

    // Funktion um Bild in den Firebase Storage hochzuladen
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

    // Funktion um Url zu neue hochgeladenem Bild im Firestore dem aktuellen Userprofil hinzuzufügen
    private fun setUserImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString())
    }

    // Funktion um neue Notiz zu speichern
//    fun saveNote(note: Note) {
//        // Mit add fügen wir ein komplett neues Dokument einer Sammlung hinzu, für dieses wird eine eigene Id generiert
//        notesRef.add(note)
//    }

    // Funktion um das Profil eines Users zu updaten
    fun updateProfile(profile: Profile) {
        profileRef.set(profile)
    }

    // Funktion um Notiz mit Hilfe der Notiz-Id zu löschen
//    fun deleteNote(note: Note) {
//        notesRef.document(note.id).delete()
//    }

    // Funktion um neuen User zu erstellen
    fun register(email: String, password: String) {
        // Firebase-Funktion um neuen User anzulegen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                // Wenn Registrierung erfolgreich ist senden wir eine Email um die Email-Adresse zu bestätigen
                firebaseAuth.currentUser?.sendEmailVerification()
                // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                profileRef = fireStore.collection("profiles").document(firebaseAuth.currentUser!!.uid)
                // Ein neues, leeres Profil wird für jeden User erstellt der zum ersten mal einen Account für die App anlegt
                profileRef.set(Profile())
                // Danach führen wir logout Funktion aus, da beim Erstellen eines Users dieser sofort eingeloggt wird
                logout()
            } else {
                // Log, falls Fehler beim Erstellen eines Users auftritt
                Log.e("FIREBASE", "${authResult.exception}")
            }
        }
    }

    // Funktion um User einzuloggen
    fun login(email: String, password: String) {
        // Firebase-Funktion um User einzuloggen
        // CompleteListener sorgt dafür, dass wir anschließend feststellen können, ob das funktioniert hat
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {

                // Überprüfung, ob User bereits Email verifiziert hat
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    // Wenn Email verifiziert, wird LiveData mit dem eingeloggten User befüllt
                    // Das triggert dann die Navigation im LoginFragment

                    // Die Profil-Referenz wird jetzt gesetzt, da diese vom aktuellen User abhängt
                    profileRef = fireStore.collection("profiles").document(firebaseAuth.currentUser!!.uid)

                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    // Wenn User zwar exisitiert und Eingaben stimmen aber User seine Email noch nicht bestätigt hat
                    // wird User wieder ausgeloggt und eine Fehlermeldung ausgegeben
                    Log.e("FIREBASE", "User not verified")
                    logout()
                }

            } else {
                // Log, falls Fehler beim Login eines Users auftritt
                Log.e("FIREBASE", "${authResult.exception}")
            }
        }
    }

    // Funktion um Passwort-Vergessen Email zu senden
    fun sendPasswordReset(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    // Funktion um User auszuloggen
    fun logout() {
        // Erst wird die Firebase-Funktion signOut aufgerufen
        firebaseAuth.signOut()
        // Danach wird der Wert der currentUser LiveData auf den aktuellen Wert des Firebase-CurrentUser gesetzt
        // Nach Logout ist dieser Wert null, also ist auch in der LiveData danach der Wert null gespeichert
        // Dies triggert die Navigation aus dem HomeFragment zurück zum LoginFragment
        _currentUser.value = firebaseAuth.currentUser
    }

}