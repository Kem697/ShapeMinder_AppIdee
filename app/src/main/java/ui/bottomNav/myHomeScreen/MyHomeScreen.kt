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
import ui.viewModel.HomeViewModel


class MyHomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
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
        searchInput()
        viewModel.contents.observe(viewLifecycleOwner) {
            var navigationBar =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navigationBar.isInvisible = false
            binding.recyclerView.adapter = ItemAdapter(it, viewModel,requireContext())

        }
    }

    override fun onResume() {
        super.onResume()
        setDefaultHint()
    }




    /*  DE :
    Die beiden Funktionen searchInput() und setDefaultHint() haben unterschiedliche Aufgaben:

    1.	searchInput():
    •	Diese Funktion ist dafür verantwortlich, das Suchfeld (searchBar) zu überwachen und auf Benutzereingaben zu reagieren.
    •	Sie fügt einen TextChangedListener zum Suchfeld hinzu, der jedes Mal aufgerufen wird, wenn sich der Text im Suchfeld ändert.
    •	Innerhalb des TextChangedListener wird der eingegebene Benutzertext abgerufen (binding.searchBarTextInput.text),
    und wenn er nicht leer ist (isNotBlank()), wird der Text des Suchfelds (binding.searchBar) auf den eingegebenen Text gesetzt.
    •	Diese Funktion reagiert also darauf, wenn der Benutzer etwas in das Suchfeld eingibt,
    und aktualisiert dann den Text des Suchfelds entsprechend.

    2.	setDefaultHint():
    •	Diese Funktion wird aufgerufen, um den Hinweis-Text des Suchfelds standardmäßig auf “Suche” zu setzen.
    •	Sie setzt den Hinweis-Text des Suchfelds auf “Suche” (binding.searchBar.hint = "Suche"),
    damit Benutzer wissen, dass sie dort suchen können.
    •	Zusätzlich überprüft sie, ob bereits Text im Suchfeld vorhanden ist (binding.searchBarTextInput.text.isNotBlank()).
    •	Wenn Text vorhanden ist, wird der Text des Suchfelds gelöscht (binding.searchBarTextInput.text.clear())
    und das Suchfeld wird zurückgesetzt (binding.searchBar.setText("")), um sicherzustellen, dass es leer ist.
    •	Diese Funktion stellt sicher, dass das Suchfeld jedes Mal, wenn sie aufgerufen wird, auf den Standardzustand zurückgesetzt wird,
    um konsistentes Verhalten zu gewährleisten, wenn das Fragment geladen oder neu geladen wird.
    */

    /* EN :
    The two functions searchInput() and setDefaultHint() have different tasks:

    1. searchInput():
    - This function is responsible for monitoring the search box (searchBar) and responding to user input.
    - It adds a TextChangedListener to the search field, which is called each time the text in the search field changes.
    - Within the TextChangedListener, the entered user text is retrieved (binding.searchBarTextInput.text),
    and if it is not empty (isNotBlank()), the text of the search field (binding.searchBar) is set to the entered text.
    - This function therefore reacts when the user enters something in the search field,
    and then updates the text of the search field accordingly.

    2. setDefaultHint():
    - This function is called to set the hint text of the search box to "Search" by default.
    - It sets the hint text of the search box to "Search" (binding.searchBar.hint = "Search"),
    so that users know that they can search there.
    - It also checks whether there is already text in the search field (binding.searchBarTextInput.text.isNotBlank()).
    - If there is text, the text of the search field is deleted (binding.searchBarTextInput.text.clear())
    and the search field is reset (binding.searchBar.setText("")) to ensure that it is empty.
    - This function ensures that the search field is reset to the default state each time it is called,
    to ensure consistent behavior when the fragment is loaded or reloaded.
    */



    fun searchInput() {
        var searchBar = binding.searchBarTextInput
        searchBar.addTextChangedListener {
            var userInput = binding.searchBarTextInput.text
            if (userInput.isNotBlank()) {
                binding.searchBar.setText(userInput)
            }
        }

    }

    fun setDefaultHint(){
        binding.searchBar.hint = getString(R.string.searchBarHint)
        if (binding.searchBarTextInput.text.isNotBlank()){
            binding.searchBarTextInput.text.clear()
            binding.searchBar.setText("")
        }

    }

}




