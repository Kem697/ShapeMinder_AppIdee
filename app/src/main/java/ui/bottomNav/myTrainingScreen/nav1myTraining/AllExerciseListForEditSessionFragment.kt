package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.CurrentSessionExerciseAdapter
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
import com.example.shapeminder_appidee.databinding.FragmentAllExerciseListForEditSessionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import ui.viewModel.HomeViewModel
import java.lang.Exception


class AllExerciseListForEditSessionFragment : Fragment() {
    private lateinit var binding: FragmentAllExerciseListForEditSessionBinding
    val viewModel: HomeViewModel by activityViewModels()


    private var lastSelectedImageBtnIndex: Int = -1
    private var lastSelectedTextBtnIndex: Int = -1

    private var lastSelectedImageBtn: ImageButton? = null
    private var lastSelectedTextBtn: Button? = null


    override fun onStart() {
        super.onStart()
        viewModel.excludeExercises(viewModel.selectedTraininingssession.value!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllExerciseListForEditSessionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        sortRadioGroup()
        setFilter()
        searchInput()
        navigateBack()
        cancelProcess()
        addExerciseSelectionSession()
    }



    override fun onStop() {
        super.onStop()
        setDefaultHint()
//        viewModel.excludeExercises(viewModel.selectedTraininingssession.value!!,requireContext())
        viewModel.retrieveRemainExercisesByBodyparts()
        val navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = false
        var tag = "Fragment Wechsel"
        Log.i(tag, "Stopp wird aufgerufen?")
    }

    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
    }




    /*Weiterarbeiten
    *
    * Für Media LiveData brauche ich :
    * LiveData -> Liste von allen Übungen
    * LiveData->Liste der gespeicherten Übungen in der Session
    *
    * Daraus ein Zusammenschluss bilden, wo jede Übung
    *
    * */


    fun setUpAdapter() {
        viewModel.remainExercisesForAddInSession.observe(viewLifecycleOwner) {
            binding.listOfAllExercises.adapter =
                CurrentSessionExerciseAdapter(it, viewModel, requireContext())
            binding.amountOfExercise.text = "${context?.getString(R.string.amountOfExercises)}: ${it.size}"
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

                    // DE: Je nachdem, welcher RadioButton ausgewählt wurde, können Sie die Übungen sortieren
                    // EN: Dependent, which radio button the user selects, the exercises will sort.
                    when (checkedId) {
                        R.id.a_z_ascending -> {
                            isSortedDescending = false
                            viewModel.sortRemainExercisesByAlphabet(isSortedDescending)
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
                            viewModel.sortRemainExercisesByAlphabet(isSortedDescending)
                        }
                    }
                    //DE: Schließen Sie den Dialog, nachdem eine Auswahl getroffen wurde
                    //EN: After the user selected his choose, the dialog will close.
                    dialog.dismiss()
                }
        }
    }
    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        val context = requireContext()
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            if (userInput.isNotBlank()) {
                binding.myTSearchBar.setText(userInput)
                var tag = "Filter???"
                Log.i(tag, "Werden die Inhalte hier gefiltert. :${userInput}")
                viewModel.filterRemainExercisesByTitle(userInput, context)
            } else {
                searchBar.text.clear()
                binding.myTSearchBar.clearText()
                viewModel.retrieveRemainExercisesByBodyparts()
                binding.resetFilterBtn.isInvisible = true
            }
        }
    }
    fun setDefaultHint() {
        binding.myTSearchBar.hint = getString(R.string.allExercisesSearchbarHint)
        if (binding.myTSearchBarTextInput.text.isNotBlank()) {
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBarTextInput.text.clearSpans()
            binding.myTSearchBar.clearText()
        }
    }
    fun setFilter() {
        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
        dialog.setContentView(R.layout.dialog_sheet_new_session_filter)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        binding.setFilterBtn.setOnClickListener {
            if (!dialog.isShowing) {
                dialog.show()

                var dialogResetBtn = dialog.findViewById<Button>(R.id.allExerciseReset_Btn)
                var dialogResultsBtn = dialog.findViewById<MaterialButton>(R.id.results_Btn)
                var dialogCancelBtn = dialog.findViewById<MaterialButton>(R.id.cancel_Btn)
                var resetBtn = binding.resetFilterBtn



                val allImageButtons = listOf<ImageButton?>(
                    dialog.findViewById(R.id.sec1_short_dumbell_Btn),
                    dialog.findViewById(R.id.sec1_long_dumbell_Btn),
                    dialog.findViewById(R.id.sec1_own_bodyweight_Btn),
                )

                var textButtons = listOf<Button?>(
                    dialog.findViewById(R.id.sec0_armsBtn),
                    dialog.findViewById(R.id.sec0_absBtn),
                    dialog.findViewById(R.id.sec0_legsBtn),
                    dialog.findViewById(R.id.sec0_chestBtn),
                    dialog.findViewById(R.id.sec0_backBtn),
                    dialog.findViewById(R.id.sec0_shoulderBtn),
                )


                val uncheckedImages = listOf(R.drawable.short_dumbell_unchecked, R.drawable.long_dumbell_unchecked,R.drawable.bodyweight_unchecked,)

                var setCheckedBackground = ContextCompat.getColor(requireContext(), R.color.tertiary)
                var setUnCheckedBackground = ContextCompat.getColor(requireContext(), R.color.white)

                val checkedImages = listOf(R.drawable.short_dumbell_checked, R.drawable.long_dumbell_checked,R.drawable.bodyweight_checked)

                userSelectionBodyParts(textButtons, setCheckedBackground, setUnCheckedBackground,)

                userSelection(allImageButtons, uncheckedImages, checkedImages,)

                dialogResultsBtn?.setOnClickListener {
                    try {
                        val muscleGroupFilter = lastSelectedTextBtn?.isSelected == true
                        val equipmentGroupFilter = lastSelectedImageBtn?.isSelected == true
                        if (muscleGroupFilter && equipmentGroupFilter){
                            viewModel.filterRemainExercisesByTwoSelections(requireContext(),
                                lastSelectedImageBtn!!, lastSelectedTextBtn!!
                            )
                        } else if (muscleGroupFilter && !equipmentGroupFilter){
                            val textButtonName = resources.getResourceEntryName(lastSelectedTextBtn!!.id)
                            val bodyPart = when (textButtonName) {
                                "sec0_armsBtn" -> requireContext().resources.getString(R.string.bpArme)
                                "sec0_absBtn" -> requireContext().resources.getString(R.string.bpBauch)
                                "sec0_legsBtn" -> requireContext().resources.getString(R.string.bpBeine)
                                "sec0_chestBtn" -> requireContext().resources.getString(R.string.bpBrust)
                                "sec0_backBtn" -> requireContext().resources.getString(R.string.bpRücken)
                                "sec0_shoulderBtn" -> requireContext().resources.getString(R.string.bpSchulter)
                                else -> ""
                            }
                            viewModel.filterRemainExercisesByBodypart(bodyPart,requireContext())
                        } else if (equipmentGroupFilter && !muscleGroupFilter){
                            val imageBtnName = requireContext().resources.getResourceEntryName(lastSelectedImageBtn!!.id)
                            when (imageBtnName) {
                                "sec1_short_dumbell_Btn" -> {
                                    viewModel.filterRemainExercisesByShortDumbbell(requireContext())
                                }
                                "sec1_long_dumbell_Btn" -> {
                                    viewModel.filterRemainExercisesByLongDumbbell(requireContext())
                                }
                                "sec1_own_bodyweight_Btn" ->{
                                    viewModel.filterRemainExercisesByBodyweight(requireContext())
                                }
                            }
                        }
                        binding.resetFilterBtn.isInvisible = false
                        dialog.dismiss()
                    } catch (e: Exception){
                        Toast.makeText(
                            binding.root.context,
                            context?.getString(R.string.toastUnknownFailure),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

                resetBtn.setOnClickListener {
                    if (lastSelectedImageBtnIndex != -1 ||lastSelectedTextBtnIndex != -1) {
                        viewModel.retrieveRemainExercisesByBodyparts()
                        if (allImageButtons.any { it?.isSelected==true }&& (textButtons.all { it?.isSelected != true })){
                            allImageButtons.forEach { imageButton ->
                                imageButton?.setImageResource(
                                    uncheckedImages[allImageButtons.indexOf(
                                        imageButton
                                    )]
                                )
                                imageButton?.isSelected = false
                            }
                        } else if (textButtons.any { it?.isSelected == true }&& allImageButtons.all { it?.isSelected !=true }){
                            textButtons.forEach { button ->
                                button?.setBackgroundColor(setUnCheckedBackground)
                                button?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                                button?.isSelected = false
                            }
                        }
                        else if (allImageButtons.any { it?.isSelected==true } && (textButtons.any { it?.isSelected == true }) ){
                            textButtons.forEach { button ->
                                button?.setBackgroundColor(setUnCheckedBackground)
                                button?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                                button?.isSelected = false
                            }
                            allImageButtons.forEach { imageButton ->
                                imageButton?.setImageResource(
                                    uncheckedImages[allImageButtons.indexOf(
                                        imageButton
                                    )]
                                )
                                imageButton?.isSelected = false
                            }
                        }

                    }
                    lastSelectedImageBtn = null
                    lastSelectedTextBtn = null
                    /*   lastSelectedImageButton!!.isSelected = false
                       lastSelectedTextButton!!.isSelected = false*/
                    resetBtn.isInvisible = true
                }

                dialogResetBtn?.setOnClickListener {
                    if (lastSelectedImageBtnIndex != -1 || lastSelectedTextBtnIndex != -1) {
                        viewModel.retrieveRemainExercisesByBodyparts()
                        if (allImageButtons.any { it?.isSelected==true }&& (textButtons.all { it?.isSelected != true })){
                            allImageButtons.forEach { imageButton ->
                                imageButton?.setImageResource(
                                    uncheckedImages[allImageButtons.indexOf(
                                        imageButton
                                    )]
                                )
                                imageButton?.isSelected = false
                            }
                        } else if (textButtons.any { it?.isSelected == true } && allImageButtons.all { it?.isSelected !=true }){
                            textButtons.forEach { button ->
                                button?.setBackgroundColor(setUnCheckedBackground)
                                button?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                                button?.isSelected = false
                            }
                        } else if (allImageButtons.any { it?.isSelected==true } && (textButtons.any { it?.isSelected == true }) ){
                            textButtons.forEach { button ->
                                button?.setBackgroundColor(setUnCheckedBackground)
                                button?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                                button?.isSelected = false
                            }
                            allImageButtons.forEach { imageButton ->
                                imageButton?.setImageResource(
                                    uncheckedImages[allImageButtons.indexOf(
                                        imageButton
                                    )]
                                )
                                imageButton?.isSelected = false
                            }

                        }
                    }
                    lastSelectedImageBtn = null
                    lastSelectedTextBtn = null
                    /*  lastSelectedImageButton!!.isSelected = false
                      lastSelectedTextButton!!.isSelected = false*/
                    resetBtn.isInvisible = true
                }

                dialogCancelBtn?.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    fun userSelectionBodyParts(allTextButtons: List<Button?>, setCheckedBackground: Int, setUnCheckedBackground: Int) {
        allTextButtons.forEachIndexed { index, selectedButton ->
            if (!selectedButton!!.isSelected) {
                selectedButton.setBackgroundColor(setUnCheckedBackground)
                selectedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            } // Setze zunächst alle Buttons auf die ungewählten Bilder
            selectedButton.setOnClickListener {
                allTextButtons.forEachIndexed { innerIndex, button -> // Setze alle Buttons auf ungewählt
                    button?.setBackgroundColor(setUnCheckedBackground)
                    button?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    button?.isSelected = false
                }
                selectedButton.setBackgroundColor(setCheckedBackground) // Setze das Bild des ausgewählten Buttons
                selectedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                selectedButton.isSelected = true
                lastSelectedTextBtn = selectedButton
                lastSelectedTextBtnIndex = index
                println("Textbutton: ${lastSelectedTextBtn?.isSelected} || ${context?.resources!!.getResourceEntryName(lastSelectedTextBtn!!.id)}")
            }
        }
    }
    fun userSelection(allButtons: List<ImageButton?>, uncheckedImages: List<Int>, checkedImages: List<Int>) {
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
                lastSelectedImageBtn = selectedButton
                lastSelectedImageBtnIndex = index
                println("Imagebutton: ${lastSelectedImageBtn?.isSelected} || ${context?.resources!!.getResourceEntryName(lastSelectedImageBtn!!.id)}")
            }
        }
    }

    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun cancelProcess(){
        var cancelBtn = binding.cancelSessionBtn
        cancelBtn.setOnClickListener {
            viewModel.remainExercisesForAddInSession.value?.forEach { it.addedToSession = false }
            viewModel.addToSessionExercises.value?.removeAll { it.addedToSession == true }
            findNavController().navigate(R.id.myTrainingScreen)
        }
    }



   /* fun mergedList(allExercises: List<Content>, currentSessionEx: TrainingsSession ): MutableList<Content> {
        viewModel.selectedTraininingssession.value.trainingsSession -> Die Übungen der ausgewählten Trainingssession
        viewModel.listOfAllExercises.value -> Alle Übungen

        Ziel->
        Die Daten müssen so transformiert werden, dass die Übungen aus der Trainingssession in der Liste
        alle Übungen markiert werden.
        Außerdem müssen neu markierte Übungen zurück in die Trainingssession gespeichert werden


        val transformedData = Transformations.map (LiveData1, LiveData2)
    }
*/

    fun addExerciseSelectionSession() {
        var addToWorkoutBtn = binding.addExerciseToSessionBtn
        addToWorkoutBtn.setOnClickListener {
            var addedToSessionExercises = viewModel.selectedTraininingssession.value?.trainingsSession ?: mutableListOf()
            var tag = "Radiocheck??"
            Log.i(tag, "Länge der Liste: ${addedToSessionExercises.size}")
            try {
                if (addedToSessionExercises.isNotEmpty()) {
                    findNavController().navigate(R.id.editTrainingSessionFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.toastSelectExerciseHint),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.toastSelectExerciseHint),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



}