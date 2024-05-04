package ui.bottomNav.myHomeScreen

import adapter.ItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentHomeScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import ui.viewModel.HomeViewModel


class MyHomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: HomeViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.setText("${requireContext().getString(R.string.homeScreenHeader)} ${auth.currentUser?.displayName?:" "}")
        viewModel.contents.observe(viewLifecycleOwner) {
            var navigationBar =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navigationBar.isInvisible = false
            binding.recyclerView.adapter = ItemAdapter(it, viewModel,requireContext())

        }
    }

    override fun onResume() {
        super.onResume()
    }


}




