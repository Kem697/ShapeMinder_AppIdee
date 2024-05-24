package de.kem697.shapeminder.ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.NewSessionExercisesAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentNewTrainingsSessionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import de.kem697.shapeminder.model.data.local.model.myTraining.Exercise
import de.kem697.shapeminder.model.data.local.model.myTraining.Performance
import de.kem697.shapeminder.model.data.local.model.myTraining.TrainingsSession
import de.kem697.shapeminder.ui.viewModel.ExercisesViewModel
import de.kem697.shapeminder.ui.viewModel.TrainingsessionViewModel
import java.text.SimpleDateFormat
import java.util.Locale


class NewTrainingsSessionFragment : Fragment() {

    private lateinit var binding: FragmentNewTrainingsSessionBinding
    val sessionViewModel: TrainingsessionViewModel by activityViewModels()

    val viewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTrainingsSessionBinding.inflate(layoutInflater)
        loadData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        setUpAdapter()
        addMoreExercise()
        showDateRangePicker()
        cancelProcess(mutableListOf())
    }



    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
    }




    override fun onStop() {
        super.onStop()
        val navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = false
        var tag = "Fragment Wechsel"
        Log.i(tag, "Stopp wird aufgerufen?")
    }




    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun setUpAdapter() {
        viewModel.addToSessionExercises.observe(viewLifecycleOwner) {
            binding.rvNewSessionExercises.adapter =
                NewSessionExercisesAdapter(it, viewModel, requireContext())
            saveTrainingSession(it)
            cancelProcess(it)
        }
    }

    fun addMoreExercise() {
        binding.addExerciseBtn.setOnClickListener {
            saveData()
            findNavController().navigate(R.id.action_newTrainingsSessionFragment_to_allExerciseListFragment)
        }
    }

    fun saveTrainingSession(addedToSessionExercises: MutableList<Exercise>) {
        var saveBtn = binding.saveWorkoutBtn
        var editSessionName = binding.editSessionName
        var sessionName = ""
        editSessionName.addTextChangedListener { editable ->
            var userInput = editable.toString()
            if (userInput.isNotBlank()) {
                sessionName = userInput
            } else {
                editSessionName.text.clear()
            }
        }


        saveBtn.setOnClickListener {
            if (sessionName.isNotBlank()|| editSessionName.text.isNotBlank()) {
                if (addedToSessionExercises.size > 0) {
                    var trainingPerformance: MutableList<Performance> = MutableList(addedToSessionExercises.size){Performance()}
                    var newSession = TrainingsSession(sessionName = editSessionName.text.toString(), sessionDate = binding.dateView.text.toString(),trainingsSession = addedToSessionExercises, performance = trainingPerformance )
                    sessionViewModel.insertNewTrainingssession(newSession)
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.toastSessionSavedHint),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetSavedInWorkoutSession(addedToSessionExercises)
                    deleteData()
                    findNavController().navigate(R.id.myTrainingScreen)
                } else {
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.toastSelectExerciseHint2),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), context?.getString(R.string.toastNameWorkoutHint), Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }


    fun showDateRangePicker() {
            var datePickerBtn = binding.setDurationBtn
            datePickerBtn.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText(getString(R.string.selectDateText))
                    .build()

                datePicker.addOnPositiveButtonClickListener { selection ->
                    val startDate = selection.first
                    val endDate = selection.second

                    // Hier kannst du mit den ausgewählten Daten arbeiten
                    // Zum Beispiel: Anzeigen des Zeitraums in einem TextView oder Speichern in Variablen
                    val startDateString =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startDate)
                    val endDateString =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(endDate)
                    // Beispiel: textView.text = "Selected range: $startDateString - $endDateString"

                    var dateView = binding.dateViewCard
                    dateView.isInvisible = false
                    var dateText = binding.dateView
                    dateText.text = "$startDateString - $endDateString"
                }

                datePicker.show(requireActivity().supportFragmentManager, "datePicker")
            }
        }


    fun cancelProcess(addedToSessionExercises: MutableList<Exercise>){
        var cancelBtn = binding.cancelSessionBtn
        cancelBtn.setOnClickListener {
            var tag = "CancelBtn"
            Log.i(tag, "Button click")
            viewModel.resetSavedInWorkoutSession(addedToSessionExercises)
            deleteData()
            findNavController().navigate(R.id.myTrainingScreen)
        }
    }



    /*DE:
    * Diese drei Funktionen dienen dazu die Daten Nutzereingaben des Datums und
    * des Trainingseinheitsnamen während Fragmentwechsel zu speichern, und auch
    * bei Bedarf zu löschen*/

    /*EN:
    * This three functions serve to save, load and delete the user input data during
    * the fragment navigation. Dependent on the needs of the user the different function
    * will be invoked. For example by clicking the cancel btn the saved data will be
    * erased to get the default state of the view.*/
    fun saveData(){
        val insertedText = binding.editSessionName.text.toString()
        binding.editSessionName.setText(insertedText)

        val insertedDate = binding.dateView.text.toString()
        binding.dateView.setText(insertedDate)

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.apply{
            putString("SessionName",insertedText)
            putString("SessionDate",insertedDate)
        }.apply()
    }

    fun loadData(){
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("SessionName",null)
        val saveDate = sharedPreferences.getString("SessionDate",null)

        binding.editSessionName.setText(savedName)
        binding.dateView.setText(saveDate)
        if (saveDate != null) {
            if (saveDate.isNotBlank())
                binding.dateViewCard.isInvisible = false
        }
    }

    fun deleteData(){
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            remove("SessionName")
            remove("SessionDate")
        }.apply()


        binding.editSessionName.setText("")
        binding.dateViewCard.isInvisible = true
    }
}




