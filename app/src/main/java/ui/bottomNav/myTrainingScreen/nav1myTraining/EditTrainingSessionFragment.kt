package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.EditTrainingAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentEditTrainingSessionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import model.data.local.model.myTraining.TrainingsSession
import ui.viewModel.ExercisesViewModel
import ui.viewModel.TrainingsessionViewModel


class EditTrainingSessionFragment : Fragment() {

    private lateinit var binding: FragmentEditTrainingSessionBinding
    val viewModel: ExercisesViewModel by activityViewModels()

    val sessionViewModel: TrainingsessionViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTrainingSessionBinding.inflate(layoutInflater)
        return binding.root
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        navigateBack()
    }


    fun setUpAdapter() {
        sessionViewModel.selectedTraininingssession.observe(viewLifecycleOwner) {
            if (it.sessionDate.isNullOrBlank()){
                binding.materialCardSessionDate.isInvisible = true
            } else{
                binding.materialCardSessionDate.isInvisible = false
                binding.sessionDate.setText(it.sessionDate)
            }
            binding.sessionName.setText(it.sessionName)
            binding.rvEditCurrentWorkout.adapter = EditTrainingAdapter(it.trainingsSession, sessionViewModel, requireContext())
            saveSessionChanges(it)
            deleteSession(it)
            addWorkout(it)
        }
    }


    fun saveSessionChanges(currentSession: TrainingsSession) {
        var saveBtn = binding.saveWorkoutBtn
        saveBtn.setOnClickListener {
            sessionViewModel.updateTrainingsession(currentSession)
            Toast.makeText(requireContext(), context?.getString(R.string.toastSessionUpdatedHint), Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.myTrainingScreen)
        }
    }


    fun deleteSession(currentSession: TrainingsSession) {
        var deletBtn = binding.deleteWorkoutBtn
        deletBtn.setOnClickListener {
            sessionViewModel.deleteTrainingsession(currentSession)
            Toast.makeText(requireContext(), context?.getString(R.string.toastSessionDeletedHint), Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.myTrainingScreen)
        }
    }

    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun addWorkout(currentSession: TrainingsSession){
        var addExerciseBtn = binding.addExerciseBtn
        addExerciseBtn.setOnClickListener {
            var action = EditTrainingSessionFragmentDirections.actionEditTrainingSessionFragmentToAllExerciseListForEditSessionFragment()
            sessionViewModel.excludeExercises(currentSession)
            findNavController().navigate(action)
        }
    }

}