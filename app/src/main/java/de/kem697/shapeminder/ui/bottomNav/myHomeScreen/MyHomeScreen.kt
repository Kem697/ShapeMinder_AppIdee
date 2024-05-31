package de.kem697.shapeminder.ui.bottomNav.myHomeScreen

import adapter.ContentAdapter
import adapter.GetGymLocationAdapter
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import coil.load
import de.kem697.shapeminder.BuildConfig
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentHomeScreenBinding
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.kem697.shapeminder.ui.viewModel.ContentViewModel
import de.kem697.shapeminder.ui.viewModel.GymLocationsViewModel


class MyHomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val contentViewModel: ContentViewModel by activityViewModels()

    private val gymPlaceViewModel: GymLocationsViewModel by activityViewModels()

    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    /*DE:
    * Mein Adapter beobachtet nun die Live Daten aus dem Repository.
    * Zur Darstellung der Daten in der View ist dies nicht zwingend notwendig.
    * Für die Navigation zu den Detailansichten (siehe ItemAdapter) hilft das
    * aber bei der Datenübertragung.
    *
    /*

    Kommentar zum home_fragment_xml.:
    <!--Durch orientation kann ich deklarieren, in welche
    Richtung sich mein Recyclerview scrollen lässt.
    Hierbei ist jedoch das Problem, dass ich nicht
    weiß, wie mein Screen sich vertikal und horizontal
    scrollen lässt. Ich werde daran noch arbeiten-->
    */

    *
    * */

    /*EN:
    * My adapter now monitors the live data from the repository.
    * This is not absolutely necessary for displaying the data in the view.
    * For navigation to the detailed views (see ItemAdapter) this helps
    * but for data transfer.*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePlacesClient(requireContext())
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = auth.currentUser
        if (user != null) {
            if (user.photoUrl != null) {
                loadProfileImage(user)
//                binding.userPhotoToolbar.load(user.photoUrl)
            }
        }
        binding.textView.setText("${requireContext().getString(R.string.homeScreenHeader)} ${auth.currentUser?.displayName?:" "}")
        setUpAdapter()
        contentViewModel.contents.observe(viewLifecycleOwner) {
            var navigationBar =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navigationBar.isInvisible = false
            binding.recyclerView.adapter = ContentAdapter(it,contentViewModel)

        }
    }


    fun setUpAdapter(){
        gymPlaceViewModel.getGymLocations.observe(viewLifecycleOwner){
            binding.gymLocationRecyclerView.adapter = GetGymLocationAdapter(it,gymPlaceViewModel,requireContext())
            binding.progressBar.visibility = View.GONE
        }

    }



    fun initializePlacesClient(context: Context) {
        val key = BuildConfig.apiKey

        // Stelle sicher, dass du den API-Schlüssel vorher in deinem Manifest hinzugefügt hast
        if (!Places.isInitialized()) {
            Places.initialize(context.applicationContext,key)
        }
    }


    private fun loadProfileImage(currentUser: FirebaseUser) {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Activity.MODE_PRIVATE)
        val uriString = sharedPreferences.getString("profileImageUri", null)
        if (uriString!=null){
            uriString?.let {
                val imageUri = Uri.parse(it)
                binding.userPhotoToolbar.load(imageUri) // Verwende Coil oder eine andere Image-Loading-Bibliothek
            }
        } else{
            if (currentUser!= null){
                if (currentUser.photoUrl!=null){
                    binding.userPhotoToolbar.load(currentUser.photoUrl) // Verwende Coil oder eine andere Image-Loading-Bibliothek
                }
            }
        }

    }

}




