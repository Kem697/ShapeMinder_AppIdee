package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.NewSessionExercisesAdapter
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentNewTrainingsSessionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import ui.viewModel.HomeViewModel


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
    }


    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true

    }





    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun setUpAdapter(){
        viewModel.addToSessionExercises.observe(viewLifecycleOwner){
            binding.rvNewSessionExercises.adapter = NewSessionExercisesAdapter(it,viewModel,requireContext())
        }
    }

    fun addMoreExercise(){
        binding.addExerciseBtn.setOnClickListener {
            findNavController().navigate(R.id.allExerciseListFragment)
        }
    }


/*
    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Hier können Sie mit dem ausgewählten Datum arbeiten
                // z.B. Anzeigen des Datums in einem TextView oder Speichern in einer Variablen
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                // Beispiel: textView.text = selectedDate
            },
            year,
            month,
            day
        )

        // Optional: Setzen Sie die maximalen und minimalen Datumsgrenzen für den Datepicker
        // Beispiel: datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
        // Beispiel: datePickerDialog.datePicker.minDate = System.currentTimeMillis()

        datePickerDialog.show()
    }
*/



}





