package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.NewSessionExercisesAdapter
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentNewTrainingsSessionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import model.data.local.model.Content
import model.data.local.model.TrainingsSession
import ui.viewModel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Locale


class NewTrainingsSessionFragment : Fragment() {

    private lateinit var binding: FragmentNewTrainingsSessionBinding
    val viewModel: HomeViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTrainingsSessionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        setUpAdapter()
        addMoreExercise()
        showDateRangePicker()

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


    fun setUpAdapter(){
        viewModel.addToSessionExercises.observe(viewLifecycleOwner){
            binding.rvNewSessionExercises.adapter = NewSessionExercisesAdapter(it,viewModel,requireContext())
            saveTrainingSession(it)
        }
    }

    fun addMoreExercise(){
        binding.addExerciseBtn.setOnClickListener {
            findNavController().navigate(R.id.allExerciseListFragment)
        }
    }

    fun saveTrainingSession(addedToSessionExercises: MutableList<Content>){
        var saveBtn = binding.saveWorkoutBtn
//        var sessionName = binding.editSessionName.toString()
        saveBtn.setOnClickListener {
            if (addedToSessionExercises.size>0){
                Toast.makeText(requireContext(), "Dein Trainingsplan wurde gespeichert!.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.myTrainingScreen)
            } else {
                Toast.makeText(requireContext(), "Bitte wähle deine Übungen!.", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun showDateRangePicker() {
        var datePickerBtn = binding.setDurationBtn
        datePickerBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second

                // Hier kannst du mit den ausgewählten Daten arbeiten
                // Zum Beispiel: Anzeigen des Zeitraums in einem TextView oder Speichern in Variablen
                val startDateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startDate)
                val endDateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(endDate)
                // Beispiel: textView.text = "Selected range: $startDateString - $endDateString"

                var dateView = binding.dateViewCard
                dateView.isInvisible = false
                var dateText  = binding.dateView
                dateText.setText("$startDateString - $endDateString")

            }

            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }


}





