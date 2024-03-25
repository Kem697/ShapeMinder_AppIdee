package ui.bottomNav.myTrainingScreen.nav3exercises

import adapter.ItemAdapter
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import model.Content
import ui.viewModel.HomeViewModel

class ExerciseListFragment : Fragment() {
    private lateinit var binding: FragmentExerciseListBinding


    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tag = "Zu den Übungen"
        Log.i(tag,"ViewCreated wird aufgerufen?")
        setUpAdapter()
        sortRadioGroup()
        searchInput()
        navigateBack()
    }


    /*Wenn der Screen angezeigt wird, soll die Hauptnavigationsleiste
    * ausgeblendet werden, damit die App sich in einem Screen festfährt.
    * */
    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
        var tag = "Pause"
        Log.e(tag, "Ist der Screen pausiert?")
    }

    /* Sobald der Screen verlassen wird, wird die Suchleiste zurückgesetzt.
   * Dadurch wird ein Absturz der App beim Fragmentwechseln verhindert, da
   * letzte ungelöschte Nutzereingabe die searchInput Funktion aufruft,
   * welche die Elemente abhängig vom Eingabewert filtert.
   * */

    override fun onStop() {
        super.onStop()
        setDefaultHint()
        var bodyPart = binding.title.text.toString()
        viewModel.resetFilter(bodyPart)
        var tag = "Fragment Wechsel"
        Log.i(tag,"Stopp wird aufgerufen?")
    }


    /*
    * Die Filterfunktion funktioniert jetzt und ich kann meine Liste abhängig
    * von der Nutzereingabe filtern. Das Problem war, dass der Eingabewert
    * nicht mit dem Namen der Übung in der View verglichen wird, sondern mit
    * des stringRessourceTitle id nummer. Es konnte nie ein vegleichen
    * stattfinden, da ich mit einer Zifferneingabe vergleichen musste.
    * Deshalb habe ich meinem ViewModel den Context übergeben. Dadurch
    * kann ich mir den festgelegten Wert meiner Stringressource Id heraus
    * holen. Näheres in der viewModel.filterExercisesByTitle(userInput, bodyPart,context)
    * Methode.

    * */


    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        val context = requireContext()
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            var bodyPart = binding.title.text.toString()
            if (userInput.isNotBlank()) {
                binding.myTSearchBar.setText(userInput)
                var tag = "Filter???"
                Log.i(tag, "Werden die Inhalte hier gefiltert. :${userInput}")
                viewModel.filterExercisesByTitle(userInput, bodyPart,context)
//                if (userInput.isNullOrEmpty()){
//                    var log = "Suchleiste leer??"
//                    Log.i(log,"Eingabe in der Suchleiste: $userInput")
//                    viewModel.resetFilter(bodyPart)
//                }
            } else {
                binding.myTSearchBar.clearText()
                updateAdapter()
            }
        }
    }


    fun setDefaultHint() {
        binding.myTSearchBar.hint = "Suche"
        if (binding.myTSearchBarTextInput.text.isNotBlank()) {
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBarTextInput.text.clearSpans()
            binding.myTSearchBar.clearText()
        }

    }

    fun sortRadioGroup() {
        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
        dialog.setContentView(R.layout.dialog_sheet)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var isSortedDescending = false

        // Listener für den Button sortByNameBtn
        binding.sortByNameBtn.setOnClickListener {
            // Um den Dialog nur einmal zu zeigen, prüfen Sie zuerst, ob er nicht bereits gezeigt wird
            if (!dialog.isShowing) {
                /*Hier wird die Farbe des "Check"-Elements auf die Farbe tertiary gesetzt.
                * */
                dialog.findViewById<RadioButton>(R.id.a_z_ascending)!!.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.tertiary)
                    )
                dialog.findViewById<RadioButton>(R.id.z_a_descending)!!.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.tertiary)
                    )
                // Anschließend wird der Dialog geöffnet.
                dialog.show()
            }

            // Listener für die Auswahl der RadioButtons innerhalb des Dialogs
            dialog.findViewById<RadioGroup>(R.id.radioG_exerciseSort)
                ?.setOnCheckedChangeListener { group, checkedId ->
                    // Hier können Sie den Code für die Sortierung der Übungen implementieren
                    val selectedBodypart =
                        viewModel.selectedContentTitle.value ?: return@setOnCheckedChangeListener
                    // Je nachdem, welcher RadioButton ausgewählt wurde, können Sie die Übungen sortieren
                    when (checkedId) {
                        R.id.a_z_ascending -> {
                            isSortedDescending = false
                            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
                            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
                        }
                        // Weitere RadioButton-Optionen können hier hinzugefügt werden
                    }
                    // Schließen Sie den Dialog, nachdem eine Auswahl getroffen wurde
                    dialog.dismiss()
                }
        }
    }

    fun updateAdapter() {
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) {exercise->
            binding.listOfExercises.adapter = ItemAdapter(exercise, viewModel)
        }
    }

    fun setUpAdapter() {
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) { exercise ->
            binding.listOfExercises.adapter = ItemAdapter(exercise, viewModel)


            /*Mit diesen Befehlen initialisiere meine ViewElemente mit
            * den initialisierten Argumenten in den jeweiligen Eigenschaften
            * meines Content Objekts. Dies führt dazu, dass der Titel und
            * die Anzahl der Übungen pro Körperpartie entsprechen der
            * Körperpartie aktualisiert wird */
            binding.title.setText(exercise.first().bodyPart)
            binding.subTitle.setText("Anzahl von Übungen: ${exercise.size}")


            /*When Verzweigung dient dazu die korrekten Bilder zu setzen,
            * wenn das  initialisierte Argument mit dem des Körperparts
            * übereinstimmt. Die Verzweigung ist noch fehlerbehaftet.*/

            when (exercise.first().bodyPart) {
                "Arme" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp1arms)
                }

                "Bauch" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp5abs)
                }

                "Schulter" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp3shoulders)
                }

                "Rücken" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp4back)
                }

                "Beine" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp2legs)
                }

                "Brust" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp6chest)
                }

                else -> {
                    binding.bodyPartView.setImageResource(R.drawable.applogo)
                }
            }

        }
    }

    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}