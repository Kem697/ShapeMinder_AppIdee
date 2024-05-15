package ui.bottomNav.myTrainingScreen.nav2exercises

import adapter.ExerciseAdapter
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
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
import com.google.android.material.button.MaterialButton
import model.data.local.model.myTraining.Exercise
import ui.viewModel.ContentViewModel
import ui.viewModel.ExercisesViewModel

class ExerciseListFragment : Fragment() {
    private lateinit var binding: FragmentExerciseListBinding
    val viewModel: ExercisesViewModel by activityViewModels()

    private lateinit var orginalExercises: List<Exercise>

    private var lastSelectedButtonIndex: Int = -1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tag = "Zu den Übungen"
        Log.i(tag, "ViewCreated wird aufgerufen?")
        setUpAdapter()
        sortRadioGroup()
        searchInput()
        navigateBack()
        setFilter()

    }

    /*DE:
    *Wenn der Screen angezeigt wird, soll die Hauptnavigationsleiste
    * ausgeblendet werden, damit die App sich in einem Screen festfährt.
    * */

    /*EN:
    *When the screen is displayed, the main navigation bar should be
    *should be hidden so that the app gets stuck in a screen.
    * */

    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true

        /*DE:
        *Mit den unten stehende zwei Zeilen setze ich meine Liste von Übungen
        * auf ihren Ursprungszustand zurück, sobald der Nutzer zum anderen Screen
        * navigiert. Hierbei über viewmodel.resetFilter meiner Liste der Übungen
        * aller Körperpartien mit dem contentTitle (also die Körperteile) gefiltert,
        * um für jede Kategorie den passenden Datensatz an Übungen zu zeigen.
        * */

        /*EN:
        *With the two lines below I reset my list of exercises
        * to their original state as soon as the user navigates to the other screen
        * navigates to the other screen. This is done via viewmodel.resetFilter of my list of exercises
        * of all body parts filtered with the contentTitle (i.e. the body parts),
        * to show the appropriate data set of exercises for each category.
        * */


        var bodyPart = viewModel.selectedExerciseTitle.value
        viewModel.retrieveExercisesByBodyparts(bodyPart!!,requireContext())
        var tag = "Pause"
        Log.e(tag, "Ist der Screen pausiert?")
    }

    /*DE:
   * Sobald der Screen verlassen wird, wird die Suchleiste zurückgesetzt.
   * Dadurch wird ein Absturz der App beim Fragmentwechseln verhindert, da
   * letzte ungelöschte Nutzereingabe die searchInput Funktion aufruft,
   * welche die Elemente abhängig vom Eingabewert filtert.
   * */

    /*EN:
    * As soon as the screen is exited, the search bar is reset.
    * This prevents the app from crashing when switching fragments, as
    * the last undeleted user input calls the searchInput function,
    * which filters the elements depending on the input value.
    * */

    override fun onStop() {
        super.onStop()
        setDefaultHint()
        var tag = "Fragment Wechsel"
        Log.i(tag, "Stopp wird aufgerufen?")
    }


    /*DE:
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

    /*EN:
    * The filter function now works and I can filter my list depending on
    * depending on the user input. The problem was that the input value
    * is not compared with the name of the exercise in the view, but with
    * of the stringResourceTitle id number. There could never be a comparison
    * because I had to compare with a numeric input.
    * That's why I passed the context to my ViewModel. This way
    * I can retrieve the specified value of my string resource id.
    * out. More details in the viewModel.filterExercisesByTitle(userInput, bodyPart,context)
    * method.

    * */


    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        val context = requireContext()
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            var bodyPart = binding.exerciseTitle.text.toString()
            if (userInput.isNotBlank()) {
                binding.myTSearchBar.setText(userInput)
                var tag = "Filter???"
                Log.i(tag, "Werden die Inhalte hier gefiltert. :${userInput}")
                viewModel.filterExercisesByTitle(userInput, bodyPart, context)
            } else {
                searchBar.text.clear()
                binding.myTSearchBar.clearText()
                viewModel.retrieveExercisesByBodyparts(bodyPart,requireContext())
                viewModel.setOriginalList(orginalExercises, bodyPart,context)
                binding.resetFilterBtn.isInvisible = true
            }
        }
    }

    fun setDefaultHint() {
        binding.myTSearchBar.hint = getString(R.string.searchBarHint)
        if (binding.myTSearchBarTextInput.text.isNotBlank()) {
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBarTextInput.text.clearSpans()
            binding.myTSearchBar.clearText()
        }
    }


    /*Doku kommentare einbinden!!*/
    fun setFilter() {
        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
        dialog.setContentView(R.layout.dialog_sheet_filter)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        binding.setFilterBtn.setOnClickListener {
            if (!dialog.isShowing) {
                dialog.show()

                var dialogResetBtn = dialog.findViewById<Button>(R.id.reset_Btn)
                var dialogResultsBtn = dialog.findViewById<MaterialButton>(R.id.results_Btn)
                var dialogCancelBtn = dialog.findViewById<MaterialButton>(R.id.cancel_Btn)

                val allImageButtons = listOf<ImageButton?>(
                    dialog.findViewById(R.id.sec1_short_dumbell_Btn),
                    dialog.findViewById(R.id.sec1_long_dumbell_Btn),
                    dialog.findViewById(R.id.sec1_own_bodyweight_Btn),
                    dialog.findViewById(R.id.sec2_with_video_Btn),
                    dialog.findViewById(R.id.sec2_no_video_Btn)
                )

                var tag = "Button gefunden?"
                Log.i(tag, "Button wurde nicht gefunden: $dialogResetBtn")

                val uncheckedImages = listOf<Int>(
                    R.drawable.short_dumbell_unchecked,
                    R.drawable.long_dumbell_unchecked,
                    R.drawable.bodyweight_unchecked,
                    R.drawable.video_unchecked,
                    R.drawable.no_video_unchecked,
                )

                val checkedImages = listOf<Int>(
                    R.drawable.short_dumbell_checked,
                    R.drawable.long_dumbell_checked,
                    R.drawable.bodyweight_checked,
                    R.drawable.video_checked,
                    R.drawable.no_video_checked,
                )


                userSelection(dialog,allImageButtons,uncheckedImages,checkedImages,dialogResultsBtn)


                var resetBtn = requireActivity().findViewById<MaterialButton>(R.id.resetFilterBtn)
                resetBtn.setOnClickListener {
                    if (lastSelectedButtonIndex != -1){
                        viewModel.retrieveExercisesByBodyparts(viewModel.selectedExerciseTitle.value!!,requireContext())
                        allImageButtons.forEach { imageButton ->
                            imageButton?.setImageResource(uncheckedImages[allImageButtons.indexOf(imageButton)])
                            imageButton?.isSelected = false
                            var tag = "Button Wahl2??"
                            Log.i(tag,"Status der Button?: ${allImageButtons[lastSelectedButtonIndex]?.isSelected}")
                        }
                    }
                    resetBtn.isInvisible = true
                }

                dialogResetBtn?.setOnClickListener {
                    if (lastSelectedButtonIndex != -1){
                        viewModel.retrieveExercisesByBodyparts(viewModel.selectedExerciseTitle.value!!,requireContext())
                        allImageButtons.forEach { imageButton ->
                            imageButton?.setImageResource(uncheckedImages[allImageButtons.indexOf(imageButton)])
                            imageButton?.isSelected = false
                            var tag = "Button Wahl??"
                            Log.i(tag,"Status der Button?: ${allImageButtons[lastSelectedButtonIndex]?.isSelected}")
                        }
                    }
                    binding.resetFilterBtn.isInvisible = true
                }

                dialogCancelBtn?.setOnClickListener {
                    dialog.dismiss()
                }
        }
    }
}

    fun userSelection(dialog: BottomSheetDialog, allButtons: List<ImageButton?>, uncheckedImages: List<Int>, checkedImages: List<Int>,resultsBtn: MaterialButton?)  {
        allButtons.forEachIndexed { index, selectedButton ->
            if (!selectedButton!!.isSelected) {
                selectedButton.setImageResource(uncheckedImages[index])
            } // Setze zunächst alle Buttons auf die ungewählten Bilder
            selectedButton.setOnClickListener {
                allButtons.forEachIndexed { innerIndex, button -> // Setze alle Buttons auf ungewählt
                    button?.setImageResource(uncheckedImages[innerIndex])
                    button?.isSelected = false
                }
                selectedButton.setImageResource(checkedImages[index]) // Setze das Bild des ausgewählten Buttons
                selectedButton.isSelected = true
                lastSelectedButtonIndex = index
                var selectedBtnName = resources.getResourceEntryName(selectedButton.id)
                var tag = "Button Wahl??"
                Log.i(tag,"Button wurde ausgewählt: ${selectedButton.isSelected} $selectedBtnName $lastSelectedButtonIndex")
                when (selectedBtnName){
                    "sec1_short_dumbell_Btn"->{
                        resultsBtn?.setOnClickListener {
                            if (selectedButton.isSelected){
                                viewModel.filterExercisesByShortDumbbell(viewModel.selectedExerciseTitle.value!!,requireContext())
                                binding.resetFilterBtn.isInvisible = false
                                dialog.dismiss()
                            } else{
                                Toast.makeText(requireContext(), context?.getString(R.string.toastNoSelectionHint), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    "sec1_long_dumbell_Btn"->{
                        resultsBtn?.setOnClickListener {
                            if (selectedButton.isSelected){
                                viewModel.filterExercisesByLongDumbbell(viewModel.selectedExerciseTitle.value!!,requireContext())
                                binding.resetFilterBtn.isInvisible = false
                                dialog.dismiss()
                            } else {
                                Toast.makeText(requireContext(), context?.getString(R.string.toastNoSelectionHint), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    "sec1_own_bodyweight_Btn"->{
                        resultsBtn?.setOnClickListener {
                            if (selectedButton.isSelected){
                                viewModel.filterExercisesByBodyweight(viewModel.selectedExerciseTitle.value!!,requireContext())
                                binding.resetFilterBtn.isInvisible = false
                                dialog.dismiss()
                            }else {
                                Toast.makeText(requireContext(), context?.getString(R.string.toastNoSelectionHint), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    "sec2_with_video_Btn"->{
                        resultsBtn?.setOnClickListener {
                            if (selectedButton.isSelected){
                                viewModel.filterExercisesByVideo(viewModel.selectedExerciseTitle.value!!,requireContext())
                                binding.resetFilterBtn.isInvisible = false
                                dialog.dismiss()
                            }else {
                                Toast.makeText(requireContext(), context?.getString(R.string.toastNoSelectionHint), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    "sec2_no_video_Btn"->{
                        resultsBtn?.setOnClickListener {
                            if (selectedButton.isSelected){
                                viewModel.filterExercisesByNoVideo(viewModel.selectedExerciseTitle.value!!,requireContext())
                                binding.resetFilterBtn.isInvisible = false
                                dialog.dismiss()
                            }else {
                                Toast.makeText(requireContext(), context?.getString(R.string.toastNoSelectionHint), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    else -> {
                        resultsBtn?.setOnClickListener {
                            viewModel.retrieveExercisesByBodyparts(viewModel.selectedExerciseTitle.value!!,requireContext())
                        }
                    }
                }
            }
        }
    }


    fun sortRadioGroup() {
        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
        dialog.setContentView(R.layout.dialog_sheet_sort)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var isSortedDescending = false

        binding.sortByNameBtn.setOnClickListener {
            // DE:Um den Dialog nur einmal zu zeigen, prüfen Sie zuerst, ob er nicht bereits gezeigt wird
            // EN:To show the dialog only once, first check that it is not already shown.

            if (!dialog.isShowing) {
                // DE: Hier wird die Farbe des "Check"-Elements auf die Farbe tertiary gesetzt.
                // EN: Here, the color of the "Check" element is set to the color tertiary.

                dialog.findViewById<RadioButton>(R.id.a_z_ascending)!!.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.tertiary)
                    )
                dialog.findViewById<RadioButton>(R.id.z_a_descending)!!.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.tertiary)
                    )
                // DE: Anschließend wird der Dialog geöffnet.
                // EN: Then the dialog opens.
                dialog.show()
            }

            // Listener für die Auswahl der RadioButtons innerhalb des Dialogs

            dialog.findViewById<RadioGroup>(R.id.radioG_exerciseSort)
                ?.setOnCheckedChangeListener { group, checkedId ->
                    // DE: Hier können Sie den Code für die Sortierung der Übungen implementieren
                    // EN: The following code sorts the exercises
                    val selectedBodypart =
                        viewModel.selectedExerciseTitle.value ?: return@setOnCheckedChangeListener
                    // DE: Je nachdem, welcher RadioButton ausgewählt wurde, können Sie die Übungen sortieren
                    // EN: Dependent, which radio button the user selects, the exercises will sort.
                    when (checkedId) {
                        R.id.a_z_ascending -> {
                            isSortedDescending = false
                            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending,requireContext())
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
                            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending,requireContext())
                        }
                    }
                    //DE: Schließen Sie den Dialog, nachdem eine Auswahl getroffen wurde
                    //EN: After the user selected his choose, the dialog will close.
                    dialog.dismiss()
                }
        }
    }

    fun setUpAdapter() {
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) { exercise ->
            orginalExercises = exercise
            binding.listOfExercises.adapter = ExerciseAdapter(exercise, viewModel,requireContext())
            binding.screenTitle.setText(viewModel.selectedExerciseTitle.value)

            /*DE:
            *Mit diesen Befehlen initialisiere meine ViewElemente mit
            * den initialisierten Argumenten in den jeweiligen Eigenschaften
            * meines Content Objekts. Dies führt dazu, dass der Titel und
            * die Anzahl der Übungen pro Körperpartie entsprechen der
            * Körperpartie aktualisiert wird */

            /*EN:
            *Use these commands to initialize my ViewElements with
            * the initialized arguments in the respective properties
            * of my content object. This results in the title and
            * the number of exercises per body part are updated according to the
            * body part is updated */

            binding.exerciseTitle.text = getString(exercise.first().bodyPart)
            binding.subTitle.text = "${context?.getString(R.string.amountOfExercises)}: ${exercise.size}"


            /*DE:
            * When Verzweigung dient dazu die korrekten Bilder zu setzen,
            * wenn das  initialisierte Argument mit dem des Körperparts
            * übereinstimmt. Die Verzweigung ist noch fehlerbehaftet.*/

            /*EN:
            * When branching is used to set the correct images,
            * if the initialized argument matches that of the body part
            * matches that of the body part. The branch is still error-prone */


            when (getString(exercise.first().bodyPart)) {
                getString(R.string.bpArme) -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp1arms)
                }

                getString(R.string.bpBauch) -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp5abs)
                }

                getString(R.string.bpSchulter) -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp3shoulders)
                }

                getString(R.string.bpRücken) -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp4back)
                }

                getString(R.string.bpBeine) -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp2legs)
                }

                getString(R.string.bpBrust) -> {
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

