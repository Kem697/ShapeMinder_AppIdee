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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
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
        sortRadioGroup()
        setUpAdapter()
        searchInput()
        navigateBack()
        onResume()
    }

    override fun onResume() {
        super.onResume()
        setDefaultHint()

    }


    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    /*Filter Funktion zur Filterung der Daten muss bearbeitet werden.*/


    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            if (userInput != null) {
                if (userInput.isNotBlank()) {
                    var searchedExercise =
                        viewModel.exercisesByBodyparts.value?.filter { it.stringRessourceTitle.toString() == userInput }
                    updatedAdapterAfterSearch(searchedExercise!!)
                    var tag = "Filter???"
                    Log.i(tag, "Werden die Inhalte hier gefiltert. :$searchedExercise")
                    binding.myTSearchBar.setText(userInput)
                } else {
                    binding.myTSearchBar.clearText()
                    updateAdapter()
                }
            }
        }
    }


//    fun searchInput() {
//        val searchBar = binding.myTSearchBarTextInput
//        searchBar.addTextChangedListener { editable ->
//            val userInput = editable.toString()
//            if (userInput.isNotBlank()) {
//                viewModel.filterExercisesByTitle(userInput)
//            } else {
//                viewModel.resetFilter()
//            }
//        }
//
//        // LiveData beobachten und auf Änderungen reagieren
//        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) { filteredExercises ->
//            filteredExercises?.let {
//                val tag = "Filter???"
//                Log.i(tag, "Werden die Inhalte hier gefiltert. : $it")
//                // Hier können Sie den Adapter aktualisieren oder andere Aktionen basierend auf den gefilterten Übungen durchführen
//            }
//        }
//    }



    fun setDefaultHint() {
        binding.myTSearchBar.hint = "Suche"
        if (binding.myTSearchBarTextInput.text.isNotBlank()) {
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBar.clearText()
        }

    }

    /*Diese Funktion ist eine Anbahnung zur Filteroption mittels einer RadioGroup.
    * Die Funktion wurde ausgebaut und ersetzt die sortByAlphabet Funktion.*/


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
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) {
            binding.listOfExercises.adapter = ItemAdapter(it, viewModel)
        }
    }

    fun updatedAdapterAfterSearch(filteredExercises: List<Content>) {
        binding.listOfExercises.adapter = ItemAdapter(filteredExercises, viewModel)
    }

    fun setUpAdapter() {
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) {
            binding.listOfExercises.adapter = ItemAdapter(it, viewModel)

            /*Mit diesen Befehlen initialisiere meine ViewElemente mit
            * den initialisierten Argumenten in den jeweiligen Eigenschaften
            * meines Content Objekts. Dies führt dazu, dass der Titel und
            * die Anzahl der Übungen pro Körperpartie entsprechen der
            * Körperpartie aktualisiert wird */
            binding.title.setText(it.first().bodyPart)
            binding.subTitle.setText("Anzahl von Übungen: ${it.size}")

            /*When Verzweigung dient dazu die korrekten Bilder zu setzen,
            * wenn das  initialisierte Argument mit dem des Körperparts
            * übereinstimmt. Die Verzweigung ist noch fehlerbehaftet.*/

            when (it.first().bodyPart) {
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


}