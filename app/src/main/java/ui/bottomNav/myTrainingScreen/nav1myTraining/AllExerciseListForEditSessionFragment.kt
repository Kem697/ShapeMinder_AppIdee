package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.CurrentSessionExerciseAdapter
import adapter.NewSessionExercisesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentAllExerciseListForEditSessionBinding
import com.example.shapeminder_appidee.databinding.FragmentEditTrainingSessionBinding
import ui.viewModel.HomeViewModel


class AllExerciseListForEditSessionFragment : Fragment() {
    private lateinit var binding: FragmentAllExerciseListForEditSessionBinding
    val viewModel: HomeViewModel by activityViewModels()





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
        navigateBack()
        cancelProcess()
    }



    /*Weiterarbeiten*/
    fun setUpAdapter() {
        viewModel.selectedTraininingssession.observe(viewLifecycleOwner) {
            binding.listOfAllExercises.adapter =
                CurrentSessionExerciseAdapter(it.trainingsSession.filter { it.addedToSession == true }, viewModel, requireContext())
            binding.amountOfExercise.text = "Anzahl der Übungen: ${it.trainingsSession.size}"
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
          /*  viewModel.listOfAllExercises.value?.forEach { it.addedToSession = false }
            viewModel.addToSessionExercises.value?.removeAll { it.addedToSession ==false }*/
            findNavController().navigate(R.id.myTrainingScreen)
        }
    }


}