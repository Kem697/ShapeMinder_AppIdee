package com.example.shapeminder_appidee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shapeminder_appidee.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /*Mit diesem Aufruf spreche ich meine Hauptnavigationsleiste an,
        * um die Indikatorfarbe des aktiven Icons meines Elements zu verändern.
        * Über den Stylesheet konnte ich diese Veränderung nicht vornehmen. Somit
        * habe ich es programmatisch gemacht. */
        binding.bottomNavigation.itemActiveIndicatorColor = getColorStateList(R.color.tertiary)




        /*Diese Codezeilen dienen zum Aufsetzen der BottumNavigationView. Erst dadurch,
        * kann über eine UI Interaktion mit NavBar der Screenwechsel innerhalb der
        * App ermöglicht werden.*/

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navHost.navController)



        /*Diese Code ermöglicht die Zurücknavigation innerhalb der App.*/

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })



    }


    /*Nachtrag zum 06.03.24:
    -Scrollview wurde implementiert,
    * sodass von obene nach unten gescrollt werden kann. Jedoch
    * kann ich die Reichweite einstellen.
    * - Das Layout für die Settings Screens wurde angefertigt.
    * Es fehlt jedoch eine Optionskarten, weil man Screen nicht weiter
    * runter scrollbar ist.
    * -Es gibt Probleme mit der Navigation, wenn man zwischen den Screens navigiert hat,
    * da eine Option von der Bottum Navigation nicht mehr anwählbar ist.*/


    /*Stand 07.03.24:
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
    *
    *
    *
    *
    * */



}

