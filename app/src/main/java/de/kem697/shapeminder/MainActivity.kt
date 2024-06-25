package de.kem697.shapeminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import de.kem697.shapeminder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*DE:
        * Mit diesem Aufruf spreche ich meine Hauptnavigationsleiste an,
        * um die Indikatorfarbe des aktiven Icons meines Elements zu verändern.
        * Über den Stylesheet konnte ich diese Veränderung nicht vornehmen. Somit
        * habe ich es programmatisch gemacht. */

        /*EN:
        * With this call I address my main navigation bar,
        * to change the indicator color of the active icon of my element.
        * I could not make this change via the stylesheet. So
        * I did it programmatically. */


        binding.bottomNavigation.itemActiveIndicatorColor = getColorStateList(R.color.tertiary)


        /*DE:
        * Diese Codezeilen dienen zum Aufsetzen der BottumNavigationView. Erst dadurch,
        * kann über eine UI Interaktion mit NavBar der Screenwechsel innerhalb der
        * App ermöglicht werden.*/

        /*EN:
        * These lines of code are used to set up the BottumNavigationView. Only through this,
        * the screen change within the app can be made possible via a UI interaction with NavBar.
        * app can be enabled. */


        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navHost.navController)



        /*DE:
        *Diese Code ermöglicht die Zurücknavigation innerhalb der App.*/

        /*EN:
        *This is code pose the implementation of the back navigation.*/

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })



    }


        /*DE:
        *Nachtrag zum 06.03.24:
        *-Scrollview wurde implementiert,
        * sodass von obene nach unten gescrollt werden kann. Jedoch
        * kann ich die Reichweite einstellen.
        * - Das Layout für die Settings Screens wurde angefertigt.
        * Es fehlt jedoch eine Optionskarten, weil man Screen nicht weiter
        * runter scrollbar ist.
        * -Es gibt Probleme mit der Navigation, wenn man zwischen den Screens navigiert hat,
        * da eine Option von der Bottum Navigation nicht mehr anwählbar ist.
        * *Stand 07.03.24:
        * -Der Settingsscreen wurde als auswählbare Option in der Bottom Nav implementiert.
        * -Es wurde Abstände zur Bottom Nav in den Layouts implementiert, sodass beim Scrollen
        * keine ViewElemente verdeckt werden.
        * -Das Layout des ContentDetailViews wurde überarbeitet, wobei u.a. eine Backtaste programmiert
        * worden ist.
        * -Die Abstände nach außen wurden in allen screens von 32dp auf 16dp verringert.
        * -Auch die RecyclerViews haben nur Abstände von 16dp zum Start und Ende des Screens.
        * -Eine GridView wurde in den Exercise Screen implementiert, um ein Raster von auswählbaren
        * Muskelgruppen darzustellen. Dazu musste ich eine passende Adapterklasse
        * erstellen, wobei diese vom sogenannten BaseAdapter erbt. Folgendes Video hat mir geholfen:
        * https://www.youtube.com/watch?v=ziJ6-AT3ymg
        * -Ein EditText wurden den SearchBars hinzugefügt, um die Suchfunktion zu programmieren. Leider
        * konnte nicht die Farbe auf weiß umstellen.
        * */
    
        /*EN:
        *addendum to 06.03.24:
        *-Scrollview has been implemented,
        * so that you can scroll from top to bottom. However
        * I can adjust the range.
        * - The layout for the settings screens has been made.
        * However, an option card is missing because the screen cannot be scrolled further down.
        * scroll down.
        * There are problems with the navigation when you have navigated between the screens,
        * because one option of the bottom navigation is no longer selectable.
        * *As of 07.03.24:
        * -The settings screen has been implemented as a selectable option in the bottom nav.
        * Distances to the bottom nav have been implemented in the layouts so that no view elements are covered when scrolling.
        * no view elements are covered when scrolling.
        * The layout of the ContentDetailView has been revised, including the programming of a back button.
        * has been programmed.
        * The distances to the outside have been reduced from 32dp to 16dp in all screens.
        * The RecyclerViews also only have distances of 16dp to the start and end of the screen.
        * A GridView has been implemented in the Exercise Screen to display a grid of selectable muscle groups.
        * muscle groups. For this I had to create a suitable adapter class
        * which inherits from the so-called BaseAdapter. The following video helped me:
        * https://www.youtube.com/watch?v=ziJ6-AT3ymg
        * -An EditText was added to the SearchBars to program the search function. Unfortunately
        * could not change the color to white.
        * */


}

