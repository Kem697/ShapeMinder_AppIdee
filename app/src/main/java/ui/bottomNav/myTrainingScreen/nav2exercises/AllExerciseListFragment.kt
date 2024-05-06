package ui.bottomNav.myTrainingScreen.nav2exercises

import adapter.NewSessionExercisesAdapter
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
import com.example.shapeminder_appidee.databinding.FragmentAllExerciseListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import ui.viewModel.HomeViewModel
import java.lang.Exception

class AllExerciseListFragment : Fragment() {
    private lateinit var binding: FragmentAllExerciseListBinding

    val viewModel: HomeViewModel by activityViewModels()



    private var lastSelectedImageButtonIndex: Int = -1
    private var lastSelectedTextButtonIndex: Int = -1

    private var lastSelectedImageButton: ImageButton? = null
    private var lastSelectedTextButton: Button? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllExerciseListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        sortRadioGroup()
        setFilter()
        searchInput()
        addExerciseSelectionSession()
        navigateBack()
        cancelProcess()
    }



    override fun onStop() {
        super.onStop()
        setDefaultHint()
        viewModel.retrieveExercisesByBodyparts()
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




    fun setUpAdapter() {
        viewModel.listOfAllExercises.observe(viewLifecycleOwner) {
            binding.listOfAllExercises.adapter =
                NewSessionExercisesAdapter(it, viewModel, requireContext())
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
                            viewModel.sortAllExercisesByAlphabet(isSortedDescending)
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
                            viewModel.sortAllExercisesByAlphabet(isSortedDescending)
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
                viewModel.filterAllExercisesByTitle(userInput, context)
            } else {
                searchBar.text.clear()
                binding.myTSearchBar.clearText()
                viewModel.retrieveExercisesByBodyparts()
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
                        val muscleGroupFilter = lastSelectedTextButton?.isSelected == true
                        val equipmentGroupFilter = lastSelectedImageButton?.isSelected == true
                        if (muscleGroupFilter && equipmentGroupFilter){
                            viewModel.filterAllExercisesByTwoSelections(requireContext(),
                                lastSelectedImageButton!!, lastSelectedTextButton!!
                            )
                        } else if (muscleGroupFilter && !equipmentGroupFilter){
                            val textButtonName = resources.getResourceEntryName(lastSelectedTextButton!!.id)
                            val bodyPart = when (textButtonName) {
                                "sec0_armsBtn" -> requireContext().resources.getString(R.string.bpArme)
                                "sec0_absBtn" -> requireContext().resources.getString(R.string.bpBauch)
                                "sec0_legsBtn" -> requireContext().resources.getString(R.string.bpBeine)
                                "sec0_chestBtn" -> requireContext().resources.getString(R.string.bpBrust)
                                "sec0_backBtn" -> requireContext().resources.getString(R.string.bpRücken)
                                "sec0_shoulderBtn" -> requireContext().resources.getString(R.string.bpSchulter)
                                else -> ""
                            }
                            viewModel.filterAllExercisesByBodypart(bodyPart,requireContext())
                        } else if (equipmentGroupFilter && !muscleGroupFilter){
                            val imageBtnName = requireContext().resources.getResourceEntryName(lastSelectedImageButton!!.id)
                            when (imageBtnName) {
                                "sec1_short_dumbell_Btn" -> {
                                    viewModel.filterAllExercisesByShortDumbbell(requireContext())
                                }
                                "sec1_long_dumbell_Btn" -> {
                                    viewModel.filterAllExercisesByLongDumbbell(requireContext())
                                }
                                "sec1_own_bodyweight_Btn" ->{
                                    viewModel.filterAllExercisesByBodyweight(requireContext())
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
                    if (lastSelectedImageButtonIndex != -1 ||lastSelectedTextButtonIndex != -1) {
                        viewModel.retrieveExercisesByBodyparts()
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
                    lastSelectedImageButton = null
                    lastSelectedTextButton = null
                    resetBtn.isInvisible = true
                }

                dialogResetBtn?.setOnClickListener {
                    if (lastSelectedImageButtonIndex != -1 || lastSelectedTextButtonIndex != -1) {
                        viewModel.retrieveExercisesByBodyparts()
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
                    lastSelectedImageButton = null
                    lastSelectedTextButton = null
                    resetBtn.isInvisible = true
                }

                dialogCancelBtn?.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    fun userSelectionBodyParts(allTextButtons: List<Button?>,setCheckedBackground: Int,setUnCheckedBackground: Int) {
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
                lastSelectedTextButton = selectedButton
                lastSelectedTextButtonIndex = index
                println("Textbutton: ${lastSelectedTextButton?.isSelected} || ${context?.resources!!.getResourceEntryName(lastSelectedTextButton!!.id)}")
            }
        }
    }
    fun userSelection(allButtons: List<ImageButton?>,uncheckedImages: List<Int>,checkedImages: List<Int>) {
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
                lastSelectedImageButton = selectedButton
                lastSelectedImageButtonIndex = index
                println("Imagebutton: ${lastSelectedImageButton?.isSelected} || ${context?.resources!!.getResourceEntryName(lastSelectedImageButton!!.id)}")
            }
        }
    }

    fun addExerciseSelectionSession() {
        var addToWorkoutBtn = binding.addExerciseToSessionBtn
        addToWorkoutBtn.setOnClickListener {
            var addedToSessionExercises = viewModel.addToSessionExercises.value ?: mutableListOf()
            var tag = "Radiocheck??"
            Log.i(tag, "Länge der Liste: ${addedToSessionExercises.size}")
            try {
                if (addedToSessionExercises.isNotEmpty()) {
                    findNavController().navigate(R.id.newTrainingsSessionFragment)
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


    fun cancelProcess(){
        var cancelBtn = binding.cancelSessionBtn
        cancelBtn.setOnClickListener {
            viewModel.listOfAllExercises.value?.forEach { it.addedToSession = false }
            viewModel.addToSessionExercises.value?.removeAll { it.addedToSession ==false }
            findNavController().navigate(R.id.myTrainingScreen)
        }
    }


    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}